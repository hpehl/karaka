package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 83
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Project extends DescriptiveModel
{
    Client client;


    public Project()
    {
        super();
    }


    public Project(String name)
    {
        super(name);
    }


    public Project(String id, String name)
    {
        super(id, name);
    }


    public Client getClient()
    {
        return client;
    }


    public void setClient(Client client)
    {
        this.client = client;
    }
}
