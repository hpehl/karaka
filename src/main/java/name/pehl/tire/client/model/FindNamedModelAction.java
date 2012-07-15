package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.NamedModel;

import com.gwtplatform.dispatch.shared.Action;

public class FindNamedModelAction<T extends NamedModel> implements Action<FindNamedModelResult<T>>
{
    String query;


    protected FindNamedModelAction()
    {
        // Possibly for serialization.
    }


    public FindNamedModelAction(String query)
    {
        this.query = query;
    }


    @Override
    public String getServiceName()
    {
        return Action.DEFAULT_SERVICE_NAME + "FindNamedModels";
    }


    @Override
    public boolean isSecured()
    {
        return true;
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
        FindNamedModelAction<T> other = (FindNamedModelAction<T>) obj;
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
        hashCode = hashCode * 37 + (query == null ? 1 : query.hashCode());
        return hashCode;
    }


    @Override
    public String toString()
    {
        return "FindNamedModelsAction[" + query + "]";
    }
}
