package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.Activity;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ActivityAction
{
    public static enum Action
    {
        EDIT,
        COPY,
        GOON,
        DELETE
    }

    @Order(1)
    Activity activity;

    @Order(2)
    Action action;
}
