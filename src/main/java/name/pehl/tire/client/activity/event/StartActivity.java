package name.pehl.tire.client.activity.event;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class StartActivity
{
    @Order(1) Activity activity;
}
