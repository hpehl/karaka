package name.pehl.karaka.server.entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
public abstract class DescriptiveEntity extends NamedEntity
{
    private String description;


    protected DescriptiveEntity(String name, String description)
    {
        super(name);
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
