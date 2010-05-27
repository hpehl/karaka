package name.pehl.tire.shared.model;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class DescriptiveEntity extends NamedEntity
{
    @Unindexed
    String description;


    public DescriptiveEntity(String name, String description)
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
