package name.pehl.tire.client.activity;

import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ActivitiesNavigation
{
    @Order(1)
    TimeUnit unit;

    @Order(2)
    Direction direction;
}
