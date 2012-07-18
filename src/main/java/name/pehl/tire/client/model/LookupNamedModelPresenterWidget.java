package name.pehl.tire.client.model;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

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
    public interface MyView extends View, HasUiHandlers<LookupNamedModelUiHandlers>
    {
        void setPlaceholder(String placeholder);
    }

    final DispatchAsync dispatcher;
    final TireActionHandler<LookupNamedModelAction<T>, LookupNamedModelResult<T>> actionHandler;
    final boolean listAllAndCache;
    final List<T> cache;


    public LookupNamedModelPresenterWidget(final EventBus eventBus, final LookupNamedModelPresenterWidget.MyView view,
            final DispatchAsync dispatcher,
            final TireActionHandler<LookupNamedModelAction<T>, LookupNamedModelResult<T>> actionHandler,
            final String placeHolder, final boolean listAllAndCache)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.actionHandler = actionHandler;
        this.listAllAndCache = listAllAndCache;
        this.cache = new ArrayList<T>();

        getView().setUiHandlers(this);
        getView().setPlaceholder(placeHolder);
    }


    @Override
    public void onRequestSuggestions(final String query, final Request request, final Callback callback)
    {
        // TODO caching
        if (query.length() > 0)
        {
            final DisplayStringFormatter formatter = new DisplayStringFormatter(query);
            ShowMessageEvent.fire(this, new Message(INFO, "Looking for \"" + query + "\"...", false));
            dispatcher.execute(new LookupNamedModelAction<T>(query), new TireCallback<LookupNamedModelResult<T>>(
                    getEventBus())
            {
                @Override
                public void onSuccess(LookupNamedModelResult<T> result)
                {
                    List<T> models = result.getModels();
                    List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
                    if (models.isEmpty())
                    {
                        // TODO Handle empty result
                        ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this, new Message(INFO,
                                "No activities found.", true));
                    }
                    else
                    {
                        ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this,
                                new Message(INFO, "Found " + models.size() + " results.", true));
                        if (models.size() == 1)
                        {
                            // TODO It's an exact match, so do not bother
                            // with showing suggestions
                            suggestions.add(newSuggestionFor(models.get(0), formatter));
                        }
                        else
                        {
                            for (T model : models)
                            {
                                suggestions.add(newSuggestionFor(model, formatter));
                            }
                        }
                        Response response = new Response(suggestions);
                        callback.onSuggestionsReady(request, response);
                    }
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    // TODO Error handling
                    ShowMessageEvent.fire(LookupNamedModelPresenterWidget.this, new Message(SEVERE, "Cannot lookup \""
                            + query + "\".", true));
                }
            });
        }
    }


    protected NamedModelSuggestion<T> newSuggestionFor(T model, DisplayStringFormatter formatter)
    {
        return new NamedModelSuggestion<T>(model.getName(), formatter.format(model.getName()), model);
    }
}
