package name.pehl.karaka.client.activity.event;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ActivityAction
{
    public static enum Action
    {
        DETAILS,
        SAVE,
        COPY,
        START_STOP,
        DELETE
    }

    @Order(1) Action action;
    @Order(2) Activity activity;
}
