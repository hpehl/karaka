package name.pehl.karaka.server.entity;

import com.googlecode.objectify.annotation.Id;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
public abstract class BaseEntity
{
    @Id private Long id;


    protected BaseEntity()
    {
    }

    public boolean isTransient()
    {
        return id == null;
    }

    /**
     * Based on the id
     *
     * @return
     *
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
     * Based on the id
     *
     * @param obj
     *
     * @return
     *
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
     * Returns {@link Class#getSimpleName()} [&lt;id&gt;]
     *
     * @return
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(getClass().getSimpleName()).append(" [").append(id).append("]").toString();
    }

    public Long getId()
    {
        return id;
    }
}
