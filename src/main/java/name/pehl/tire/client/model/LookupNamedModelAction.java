package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.NamedModel;

import com.gwtplatform.dispatch.shared.Action;

/**
 * Please note: Subclasses for each kind of lookup are necessary to get the
 * action handler registration stuff right!
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class LookupNamedModelAction<T extends NamedModel> implements Action<LookupNamedModelResult<T>>
{
    String query;


    public LookupNamedModelAction()
    {
        this(null);
    }


    public LookupNamedModelAction(String query)
    {
        this.query = query;
    }


    @Override
    public String getServiceName()
    {
        return Action.DEFAULT_SERVICE_NAME + "LookupNamedModels";
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
        LookupNamedModelAction<T> other = (LookupNamedModelAction<T>) obj;
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
        return "LookupNamedModelsAction[" + query + "]";
    }
}
