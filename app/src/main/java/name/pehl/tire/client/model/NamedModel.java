package name.pehl.tire.client.model;

/**
 * @author $Author$
 * @version $Date$ $Revision: 117
 *          $
 */
public abstract class NamedModel extends BaseModel
{
    private String name;


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
}
