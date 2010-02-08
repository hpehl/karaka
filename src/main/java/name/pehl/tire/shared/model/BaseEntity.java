package name.pehl.tire.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public abstract class BaseEntity implements Serializable
{
    @Id
    Long id;


    public BaseEntity()
    {
        super();
    }


    /**
     * Based on the id
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    /**
     * Based on the id
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
        BaseEntity other = (BaseEntity) obj;
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
     * Returns {@link #simpleClassname()} [&lt;id&gt;]
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(simpleClassname()).append(" [").append(id).append("]").toString();
    }


    public Long getId()
    {
        return id;
    }


    /**
     * Returns the simple class name for this class. Workaround since
     * {@link Class#getSimpleName()} is not part of the GWT JRE emulation
     * library.
     * 
     * @return
     */
    protected String simpleClassname()
    {
        String classname = getClass().getName();
        int lastPeriod = classname.lastIndexOf('.');
        return classname.substring(lastPeriod + 1, classname.length());
    }
}
