package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

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
    TimeUnit unit;
}
