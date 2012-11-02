package name.pehl.karaka.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 83
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Client extends DescriptiveModel
{
    public Client()
    {
        super();
    }


    public Client(final String name)
    {
        super(name);
    }


    public Client(final String id, final String name)
    {
        super(id, name);
    }
}
