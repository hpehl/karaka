package name.pehl.tire.client.activity;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author$
 * @version $Date$ $Revision: 179
 *          $
 */
@GenEvent
@SuppressWarnings("unused")
public class ActivitiesNavigation
{
    public static enum Direction
    {
        PREV,
        CURRENT,
        NEXT;
    }

    public static enum Unit
    {
        WEEK,
        MONTH;
    }

    @Order(1)
    private Direction direction;

    @Order(2)
    private Unit unit;
}
