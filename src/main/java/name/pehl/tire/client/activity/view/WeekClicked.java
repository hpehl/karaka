package name.pehl.tire.client.activity.view;

import name.pehl.tire.shared.model.Week;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class WeekClicked
{
    @Order(1) Week week;
}
