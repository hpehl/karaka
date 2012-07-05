package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.shared.model.NamedModel;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class LookupModelPresenterWidget<T extends NamedModel, A extends Action<R>, R extends Result> extends
        PresenterWidget<LookupModelPresenterWidget.MyView<T>>
{
    public interface MyView<T> extends View
    {
    }

    final DispatchAsync dispatcher;
    final TireActionHandler<A, R> actionHandler;
    final boolean cacheModels;
    final List<T> cache;


    public LookupModelPresenterWidget(final EventBus eventBus, final MyView<T> view, final DispatchAsync dispatcher,
            final TireActionHandler<A, R> actionHandler, final boolean cacheModels)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.actionHandler = actionHandler;
        this.cacheModels = cacheModels;
        this.cache = new ArrayList<T>();
    }
}
