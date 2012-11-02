package name.pehl.karaka.server.entity;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
public abstract class DescriptiveEntity extends NamedEntity
{
    private static final long serialVersionUID = 9133025000040397867L;

    @Unindexed private String description;


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
