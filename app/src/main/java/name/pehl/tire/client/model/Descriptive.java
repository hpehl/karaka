package name.pehl.tire.client.model;

import com.gwtplatform.annotation.GenDto;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
@GenDto
public class Descriptive extends Named
{
    @Order(3)
    String description;
}
