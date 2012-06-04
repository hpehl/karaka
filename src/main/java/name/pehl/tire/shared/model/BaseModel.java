package name.pehl.tire.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * <h3>Design by contract</h3>
 * <ul>
 * <li>Invariants: {@link #getId()} is never <code>null</code>. Calling
 * {@link #setId(String)} with <code>null</code> will throw an
 * {@link IllegalArgumentException}.
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseModel
{
    static final Random random = new Random();
    static final String TRANSIENT_ID_PREFIX = "__transient__";

    // ------------------------------------------------------- member variables

    String id;
    List<Link> links;


    // ------------------------------------------------------------ constructor

    BaseModel()
    {
        this(newId());
    }


    /**
     * @param id
     *            The id - must not be <code>null</code>
     * @throws IllegalArgumentException
     *             if the id is <code>null</code>
     */
    BaseModel(String id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Id must not be null!");
        }
        this.id = id;
        this.links = new ArrayList<Link>();
    }


    static String newId()
    {
        return TRANSIENT_ID_PREFIX + random.nextInt(100) + "__" + System.currentTimeMillis() + "__";
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


    /**
     * @param id
     *            The id - must not be <code>null</code>
     * @throws IllegalArgumentException
     *             if the id is <code>null</code>
     */
    public void setId(String id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("Id must not be null!");
        }
        this.id = id;
        this.id = id;
    }


    public boolean isTransient()
    {
        return this.id.startsWith(TRANSIENT_ID_PREFIX);
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
