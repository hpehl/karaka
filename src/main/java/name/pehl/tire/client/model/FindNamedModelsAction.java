package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.NamedModel;

import com.gwtplatform.dispatch.shared.Action;

public class FindNamedModelsAction<T extends NamedModel> implements Action<FindNamedModelsResult<T>>
{
    String resource;
    String query;


    protected FindNamedModelsAction()
    {
        // Possibly for serialization.
    }


    public FindNamedModelsAction(String resource, String query)
    {
        this.resource = resource;
        this.query = query;
    }


    @Override
    public String getServiceName()
    {
        return Action.DEFAULT_SERVICE_NAME + "GetNamedModels";
    }


    @Override
    public boolean isSecured()
    {
        return true;
    }


    public java.lang.String getResource()
    {
        return resource;
    }


    public java.lang.String getQuery()
    {
        return query;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        FindNamedModelsAction<T> other = (FindNamedModelsAction<T>) obj;
        if (resource == null)
        {
            if (other.resource != null)
            {
                return false;
            }
        }
        else if (!resource.equals(other.resource))
        {
            return false;
        }
        if (query == null)
        {
            if (other.query != null)
            {
                return false;
            }
        }
        else if (!query.equals(other.query))
        {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hashCode = 23;
        hashCode = hashCode * 37 + (resource == null ? 1 : resource.hashCode());
        hashCode = hashCode * 37 + (query == null ? 1 : query.hashCode());
        return hashCode;
    }


    @Override
    public String toString()
    {
        return "GetNamedModelsAction[" + resource + "," + query + "]";
    }
}
