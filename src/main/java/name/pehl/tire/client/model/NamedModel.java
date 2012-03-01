package name.pehl.tire.client.model;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
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
