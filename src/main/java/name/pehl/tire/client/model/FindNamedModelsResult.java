package name.pehl.tire.client.model;

import java.util.List;

import name.pehl.tire.shared.model.NamedModel;

import com.gwtplatform.dispatch.shared.Result;

public class FindNamedModelsResult<T extends NamedModel> implements Result
{
    List<T> models;


    protected FindNamedModelsResult()
    {
        // Possibly for serialization.
    }


    public FindNamedModelsResult(List<T> models)
    {
        this.models = models;
    }


    public List<T> getModels()
    {
        return models;
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
        FindNamedModelsResult<T> other = (FindNamedModelsResult<T>) obj;
        if (models == null)
        {
            if (other.models != null)
            {
                return false;
            }
        }
        else if (!models.equals(other.models))
        {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode()
    {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (models == null ? 1 : models.hashCode());
        return hashCode;
    }


    @Override
    public String toString()
    {
        return "GetNamedModelsResult[" + models + "]";
    }
}
