package name.pehl.tire.client.model;

/**
 * @author $Author$
 * @version $Date$ $Revision: 117
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
