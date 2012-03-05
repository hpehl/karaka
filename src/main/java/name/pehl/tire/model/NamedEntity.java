package name.pehl.tire.model;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@SuppressWarnings("serial")
public abstract class NamedEntity extends BaseEntity implements Searchable
{
    @Unindexed
    private String name;


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
    }


    /**
     * Returns the name
     * 
     * @return the name
     * @see name.pehl.tire.model.Searchable#getSearchData()
     */
    @Override
    public String getSearchData()
    {
        return name;
    }
}