package name.pehl.tire.client.activity.week;

import name.pehl.tire.client.navigation.Direction;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenEvent
@SuppressWarnings("unused")
public class WeekNavigation
{
    @Order(1)
    private Direction direction;
}
