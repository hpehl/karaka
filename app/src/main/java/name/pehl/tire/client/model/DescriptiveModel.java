package name.pehl.tire.client.model;

import name.pehl.piriti.client.json.JsonField;

/**
 * @author $Author$
 * @version $Date$ $Revision: 78
 *          $
 */
public abstract class DescriptiveModel extends NamedModel
{
    @JsonField
    public String description;


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }
}
