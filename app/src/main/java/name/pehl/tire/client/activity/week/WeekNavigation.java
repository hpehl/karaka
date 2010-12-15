package name.pehl.tire.client.activity.week;

import name.pehl.tire.client.activity.Direction;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class WeekNavigation
{
    @Order(1)
    Direction direction;
}
