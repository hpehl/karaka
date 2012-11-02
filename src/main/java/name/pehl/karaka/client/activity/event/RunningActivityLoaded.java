package name.pehl.karaka.client.activity.event;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 180
 *          $
 */
@GenEvent
public class RunningActivityLoaded
{
    @Order(1) Activity activity;
}
