package name.pehl.tire.shared.model;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class NamedEntity extends BaseEntity
{
    String name;


    public NamedEntity(String name)
    {
        super();
        this.name = name;
    }


    /**
     * Returns {@link #simpleClassname()} [&lt;id&gt;, &lt;name&gt;]
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(simpleClassname()).append(" [").append(id).append(", ").append(name).append("]")
                .toString();
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
