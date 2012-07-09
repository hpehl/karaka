package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;

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
public abstract class FindNamedModelsPresenterWidget<T extends NamedModel> extends
        PresenterWidget<FindNamedModelsPresenterWidget.MyView<T>> implements FindNamedModelsUiHandlers
{
    public interface MyView<T> extends View, HasUiHandlers<FindNamedModelsUiHandlers>
    {
        void setPlaceholder(String placeholder);
    }

    final DispatchAsync dispatcher;
    final TireActionHandler<FindNamedModelsAction<T>, FindNamedModelsResult<T>> actionHandler;
    final boolean multivalued;


    public FindNamedModelsPresenterWidget(final EventBus eventBus, final MyView<T> view,
            final DispatchAsync dispatcher,
            final TireActionHandler<FindNamedModelsAction<T>, FindNamedModelsResult<T>> actionHandler,
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
            dispatcher.execute(new FindNamedModelsAction<T>(query), new TireCallback<FindNamedModelsResult<T>>(
                    getEventBus())
            {
                @Override
                public void onSuccess(FindNamedModelsResult<T> result)
                {
                    List<T> models = result.getModels();
                    List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
                    if (models.isEmpty())
                    {
                        // TODO Handle empty result
                    }
                    else if (models.size() == 1)
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
