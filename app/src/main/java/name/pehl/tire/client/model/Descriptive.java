package name.pehl.tire.client.model;

import com.gwtplatform.annotation.GenDto;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-17 15:31:02 +0200 (Fr, 17 Sep 2010) $ $Revision: 69 $
 */
@GenDto
public class Descriptive extends Named
{
    @Order(3)
    String description;
}
