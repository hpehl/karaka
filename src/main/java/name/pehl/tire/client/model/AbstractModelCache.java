package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public abstract class AbstractModelCache<T extends BaseModel> implements ModelCache<T>
{
    final EventBus eventBus;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final List<T> models;


    public AbstractModelCache(EventBus eventBus, Scheduler scheduler, DispatchAsync dispatcher)
    {
        super();
        this.eventBus = eventBus;
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.models = new ArrayList<T>();
    }


    @Override
    public T single()
    {
        if (models.isEmpty())
        {
            return null;
        }
        return models.get(0);
    }


    @Override
    public List<T> list()
    {
        return models;
    }
}
