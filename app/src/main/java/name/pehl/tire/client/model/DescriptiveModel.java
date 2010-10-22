package name.pehl.tire.client.model;

import name.pehl.piriti.client.json.Json;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public abstract class DescriptiveModel extends NamedModel
{
    @Json
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
