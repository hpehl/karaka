package name.pehl.karaka.client.model;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.shared.model.BaseModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractModelCache<T extends BaseModel> implements ModelCache<T>
{
    protected final Scheduler scheduler;
    protected final EventBus eventBus;
    protected final DispatchAsync dispatcher;
    protected final List<T> models;


    public AbstractModelCache(Scheduler scheduler, EventBus eventBus, DispatchAsync dispatcher)
    {
        super();
        this.scheduler = scheduler;
        this.eventBus = eventBus;
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


    @Override
    public Iterator<T> iterator()
    {
        return models.iterator();
    }


    @Override
    public boolean isEmpty()
    {
        return models.isEmpty();
    }
}
