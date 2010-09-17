package name.pehl.tire.client.model;

import com.gwtplatform.annotation.GenDto;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenDto
public class Named extends Base
{
    @Order(2)
    String name;
}
