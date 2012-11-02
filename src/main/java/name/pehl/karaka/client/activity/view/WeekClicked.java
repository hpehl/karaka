package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.shared.model.Week;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class WeekClicked
{
    @Order(1) Week week;
}
