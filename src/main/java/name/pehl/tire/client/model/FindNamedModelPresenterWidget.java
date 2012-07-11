package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;
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
public abstract class FindNamedModelPresenterWidget<T extends NamedModel> extends
        PresenterWidget<FindNamedModelPresenterWidget.MyView> implements FindNamedModelUiHandlers
{
    public interface MyView extends View, HasUiHandlers<FindNamedModelUiHandlers>
    {
        void setPlaceholder(String placeholder);
    }

    final DispatchAsync dispatcher;
    final TireActionHandler<FindNamedModelAction<T>, FindNamedModelResult<T>> actionHandler;
    final boolean multivalued;


    public FindNamedModelPresenterWidget(final EventBus eventBus, final FindNamedModelPresenterWidget.MyView view,
            final DispatchAsync dispatcher,
            final TireActionHandler<FindNamedModelAction<T>, FindNamedModelResult<T>> actionHandler,
            final String placeHolder, final boolean multivalued)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.actionHandler = actionHandler;
        this.multivalued = multivalued;

        getView().setUiHandlers(this);
        getView().setPlaceholder(placeHolder);
    }


    @Override
    public void onRequestSuggestions(final String query, final Request request, final Callback callback)
    {
        // TODO caching
        if (query.length() > 0)
        {
            ShowMessageEvent.fire(this, new Message(Level.INFO, "Looking for activities...", false));
            dispatcher.execute(new FindNamedModelAction<T>(query), new TireCallback<FindNamedModelResult<T>>(
                    getEventBus())
            {
                @Override
                public void onSuccess(FindNamedModelResult<T> result)
                {
                    List<T> models = result.getModels();
                    List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
                    if (models.isEmpty())
                    {
                        // TODO Handle empty result
                        ShowMessageEvent.fire(FindNamedModelPresenterWidget.this, new Message(Level.INFO,
                                "No activities found.", true));
                    }
                    else
                    {
                        ShowMessageEvent.fire(FindNamedModelPresenterWidget.this, new Message(Level.INFO, "Found "
                                + models.size() + " activities.", true));
                        if (models.size() == 1)
                        {
                            // TODO It's an exact match, so do not bother
                            // with showing suggestions
                            suggestions.add(new NamedModelSuggestion<T>(models.get(0)));
                        }
                        else
                        {
                            for (T model : models)
                            {
                                suggestions.add(new NamedModelSuggestion<T>(model));
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
                }
            });
        }
    }

    static class NamedModelSuggestion<T extends NamedModel> extends MultiWordSuggestion
    {
        final T model;


        public NamedModelSuggestion(T model)
        {
            super(model.getName(), model.getName());
            this.model = model;
        }


        public T getModel()
        {
            return model;
        }
    }
}
