package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import name.pehl.tire.shared.model.BaseModel;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public abstract class AbstractModelCache<T extends BaseModel> implements ModelCache<T>
{
    protected final Logger logger;
    protected final EventBus eventBus;
    protected final DispatchAsync dispatcher;
    protected final List<T> models;


    public AbstractModelCache(EventBus eventBus, DispatchAsync dispatcher)
    {
        super();
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;
        this.models = new ArrayList<T>();
        this.logger = Logger.getLogger(getClass().getName());
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
