package name.pehl.karaka.client.activity.event;

import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class ActivityChanged
{
    public static enum ChangeAction
    {
        NEW,
        CHANGED,
        STARTED,
        RESUMED,
        STOPPED,
        DELETE
    }

    @Order(1) ChangeAction action;
    @Order(2) Activity activity;
    @Order(3) Activities activities;
}
