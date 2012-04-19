package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 117
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NamedModel extends BaseModel
{
    String name;


    NamedModel()
    {
        super();
    }


    NamedModel(String name)
    {
        super();
        this.name = name;
    }


    NamedModel(String id, String name)
    {
        super(id);
        this.name = name;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
}
