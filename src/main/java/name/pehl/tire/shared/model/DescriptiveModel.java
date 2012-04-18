package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DescriptiveModel extends NamedModel
{
    String description;


    public DescriptiveModel(String name)
    {
        super(name);
    }


    public DescriptiveModel(String id, String name)
    {
        super(id, name);
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
