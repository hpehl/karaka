package name.pehl.tire.client.model;

import name.pehl.piriti.client.json.Json;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public abstract class NamedModel extends BaseModel
{
    @Json
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
