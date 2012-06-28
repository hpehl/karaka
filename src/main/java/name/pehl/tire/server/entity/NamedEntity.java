package name.pehl.tire.server.entity;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@SuppressWarnings("serial")
public abstract class NamedEntity extends BaseEntity
{
    @Unindexed private String name;


    protected NamedEntity(String name)
    {
        super();
        this.name = name;
    }


    /**
     * Returns {@link Class#getSimpleName()} [&lt;id&gt;, &lt;name&gt;]
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(getClass().getSimpleName()).append(" [").append(getId()).append(", ").append(name)
                .append("]").toString();
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
}
