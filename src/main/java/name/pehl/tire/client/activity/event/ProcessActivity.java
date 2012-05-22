package name.pehl.tire.client.activity.event;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ProcessActivity
{
    public static enum Action
    {
        EDIT,
        COPY,
        START_STOP,
        DELETE
    }

    @Order(1) int rowIndex;

    @Order(2) Activity activity;

    @Order(3) Action action;
}
