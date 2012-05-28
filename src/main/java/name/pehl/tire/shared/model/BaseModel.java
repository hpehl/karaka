package name.pehl.tire.shared.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseModel
{
    // ------------------------------------------------------- member variables

    String id;
    List<Link> links;


    // ------------------------------------------------------------ constructor

    BaseModel()
    {
        this(null);
    }


    BaseModel(String id)
    {
        this.id = id;
        this.links = new ArrayList<Link>();
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


    public void setId(String id)
    {
        this.id = id;
    }


    public boolean isTransient()
    {
        return this.id == null;
    }


    public List<Link> getLinks()
    {
        return links;
    }


    public void addLink(String rel, String url)
    {
        if (rel != null && url != null)
        {
            Link link = new Link(rel, url);
            if (!this.links.contains(link))
            {
                this.links.add(link);
            }
        }
    }


    public void setLinks(List<Link> links)
    {
        this.links = links;
    }
}
