package name.pehl.tire.client.activity;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author$
 * @version $Date$ $Revision: 180
 *          $
 */
@GenEvent
public class ActivitiesLoaded
{
    @Order(1)
    Activities activities;

    @Order(2)
    Unit unit;
}
