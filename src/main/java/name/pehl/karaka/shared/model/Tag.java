package name.pehl.karaka.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 173
 *          $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag extends NamedModel
{
    public Tag()
    {
        super();
    }


    public Tag(final String name)
    {
        super(name);
    }


    public Tag(final String id, final String name)
    {
        super(id, name);
    }
}
