package name.pehl.tire.shared.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
public abstract class BaseModel
{
    // ------------------------------------------------------- member variables

    private final String id;
    private final Map<String, String> links;


    // ------------------------------------------------------------ constructor

    protected BaseModel()
    {
        this(null);
    }


    protected BaseModel(String id)
    {
        this.id = id;
        this.links = new HashMap<String, String>();
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on the id.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }


    /**
     * Based on the id.
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
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
        BaseModel other = (BaseModel) obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }


    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName()).append("[").append(id).append("]");
        return builder.toString();
    }


    // ------------------------------------------------------------- properties

    public String getId()
    {
        return id;
    }


    public Map<String, String> getLinks()
    {
        return links;
    }


    public void addLink(String rel, String url)
    {
        this.links.put(rel, url);
    }
}
