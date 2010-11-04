package name.pehl.tire.model;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
public abstract class NamedEntity extends BaseEntity
{
    @Unindexed
    private String name;
    @SuppressWarnings("unused")
    private String searchableName;


    public NamedEntity(String name)
    {
        super();
        setName(name);
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
        this.searchableName = makeSearchable(name);
    }


    public static String makeSearchable(String name)
    {
        if (name != null)
        {
            return name.toLowerCase();
        }
        return null;
    }
}
