package name.pehl.tire.client.model;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static name.pehl.tire.client.model.LookupNamedModelPresenterWidget.SearchMode.CLIENT_SIDE_SEARCH;
import static name.pehl.tire.client.model.LookupNamedModelPresenterWidget.SearchMode.SERVER_SIDE_SEARCH;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class LookupNamedModelPresenterWidget<T extends NamedModel> extends
        PresenterWidget<LookupNamedModelPresenterWidget.MyView> implements LookupNamedModelUiHandlers
{
    public enum SearchMode
    {
        CLIENT_SIDE_SEARCH,
        SERVER_SIDE_SEARCH;
    }

    public interface MyView extends View, HasUiHandlers<LookupNamedModelUiHandlers>
    {
        void setPlaceholder(String placeholder);
    }

    final DispatchAsync dispatcher;
    final TireActionHandler<LookupNamedModelAction<T>, LookupNamedModelResult<T>> actionHandler;
    final SearchMode searchMode;
    final List<T> cache;


    public LookupNamedModelPresenterWidget(final EventBus eventBus, final LookupNamedModelPresenterWidget.MyView view,
            final DispatchAsync dispatcher,
            final TireActionHandler<LookupNamedModelAction<T>, LookupNamedModelResult<T>> actionHandler,
            final String placeHolder, final SearchMode searchMode)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.actionHandler = actionHandler;
        this.searchMode = searchMode;
        this.cache = new ArrayList<T>();

        getView().setUiHandlers(this);
        getView().setPlaceholder(placeHolder);
    }


    @Override
    public void onRequestSuggestions(final Request request, final Callback callback)
    {
        final DisplayStringFormatter displayStringFormatter = new DisplayStringFormatter(request.getQuery());
        if (searchMode == CLIENT_SIDE_SEARCH)
        {
            if (cache.isEmpty())
            {
                ShowMessageEvent.fire(this, new Message(INFO, "Looking for \"" + request.getQuery() + "\"...", false));
                dispatcher.execute(new LookupNamedModelAction<T>(request.getQuery()), new LookupNamedModelCallback(
                        request, callback)
                {
                    @Override
                    void onSuccess(List<T> models)
                    {
                        LookupNamedModelPresenterWidget.this.cache.addAll(models);
                        List<NamedModelSuggestion<T>> suggestions = filter(request.getQuery(), models,
                                displayStringFormatter);
                        ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this, new Message(INFO, "Found "
                                + suggestions.size() + " results.", true));
                        callback.onSuggestionsReady(request, new Response(suggestions));
                    }
                });
            }
            else
            {
                List<NamedModelSuggestion<T>> suggestions = filter(request.getQuery(),
                        LookupNamedModelPresenterWidget.this.cache, displayStringFormatter);
                callback.onSuggestionsReady(request, new Response(suggestions));
            }
        }
        else if (searchMode == SERVER_SIDE_SEARCH)
        {
            ShowMessageEvent.fire(this, new Message(INFO, "Looking for \"" + request.getQuery() + "\"...", false));
            dispatcher.execute(new LookupNamedModelAction<T>(request.getQuery()), new LookupNamedModelCallback(request,
                    callback)
            {
                @Override
                void onSuccess(List<T> models)
                {
                    ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this,
                            new Message(INFO, "Found " + models.size() + " results.", true));
                    List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
                    for (T model : models)
                    {
                        suggestions.add(newSuggestionFor(model, displayStringFormatter));
                    }
                    callback.onSuggestionsReady(request, new Response(suggestions));
                }
            });
        }
    }


    protected List<NamedModelSuggestion<T>> filter(String query, List<T> models,
            DisplayStringFormatter displayStringFormatter)
    {
        List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
        for (T model : models)
        {
            if (model.getName().contains(query))
            {
                suggestions.add(newSuggestionFor(model, displayStringFormatter));
            }
        }
        return suggestions;
    }


    protected NamedModelSuggestion<T> newSuggestionFor(T model, DisplayStringFormatter displayStringFormatter)
    {
        return new NamedModelSuggestion<T>(model, model.getName(), displayStringFormatter.format(model.getName()));
    }

    abstract class LookupNamedModelCallback extends TireCallback<LookupNamedModelResult<T>>
    {
        final Request request;
        final Callback callback;


        LookupNamedModelCallback(Request request, Callback callback)
        {
            super(getEventBus());
            this.request = request;
            this.callback = callback;
        }


        @Override
        public void onSuccess(LookupNamedModelResult<T> result)
        {
            List<T> models = result.getModels();
            if (models.isEmpty())
            {
                ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this, new Message(INFO, "Nothing found for \""
                        + request.getQuery() + "\".", true));
            }
            else
            {
                onSuccess(models);
            }
        }


        abstract void onSuccess(List<T> models);


        @Override
        public void onFailure(Throwable caught)
        {
            // TODO Error handling
            ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this, new Message(SEVERE, "Cannot lookup \""
                    + request.getQuery() + "\".", true));
        }
    }
}
