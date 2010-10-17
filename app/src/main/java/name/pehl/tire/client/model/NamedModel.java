package name.pehl.tire.client.model;

import name.pehl.piriti.client.json.JsonField;

/**
 * @author $Author$
 * @version $Date$ $Revision: 78
 *          $
 */
public abstract class NamedModel extends BaseModel
{
    @JsonField
    public String name;


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
}
