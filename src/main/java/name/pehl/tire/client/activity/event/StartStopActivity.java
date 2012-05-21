package name.pehl.tire.client.activity.event;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class StartStopActivity
{
    public static enum Action
    {
        START,
        STOP
    }

    @Order(2) Action action;
}
