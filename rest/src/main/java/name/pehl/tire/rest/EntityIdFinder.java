package name.pehl.tire.rest;

import static java.util.logging.Level.*;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.dao.AbstractObjectifyDao;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class EntityIdFinder<T>
{
    public T findById(ServerResource resource, AbstractObjectifyDao<T> dao, String id) throws ResourceException
    {
        if (id == null)
        {
            resource.getLogger().log(SEVERE, "No id given");
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        try
        {
            long idValue = Long.parseLong(id);
            return dao.get(idValue);
        }
        catch (NumberFormatException e)
        {
            resource.getLogger().log(SEVERE, String.format("Cannot parse id %s", id), e);
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, e);
        }
        catch (EntityNotFoundException e)
        {
            resource.getLogger().log(SEVERE, String.format("Entity with id %s not found", id), e);
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, e);
        }
    }


    public List<T> findByIds(ServerResource resource, AbstractObjectifyDao<T> dao, String ids) throws ResourceException
    {
        if (ids == null)
        {
            resource.getLogger().log(SEVERE, "No id(s) given");
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        String[] seperatedIds = ids.split(",");
        if (seperatedIds == null || seperatedIds.length == 0)
        {
            resource.getLogger().log(SEVERE, "No id(s) given");
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        List<T> entities = new ArrayList<T>();
        for (String id : seperatedIds)
        {
            entities.add(findById(resource, dao, id));
        }
        return entities;
    }
}
