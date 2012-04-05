package name.pehl.tire.shared.model;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
public abstract class DescriptiveModel extends NamedModel
{
    private String description;


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }
}
