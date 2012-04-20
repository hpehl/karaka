package name.pehl.tire.client.activity.event;

import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.TimeUnit;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 180
 *          $
 */
@GenEvent
public class ActivitiesLoaded
{
    @Order(1) Activities activities;

    @Order(2) TimeUnit unit;
}
