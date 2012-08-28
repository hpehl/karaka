package name.pehl.tire.client.activity.event;

import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class Tick
{
    @Order(1) Activity activity;
    @Order(2) Activities activities;
}
