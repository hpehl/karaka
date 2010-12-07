package name.pehl.tire.client.activity;

import name.pehl.tire.client.activity.month.Month;
import name.pehl.tire.client.activity.week.Week;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenEvent
@SuppressWarnings("unused")
public class ActivitiesLoaded
{
    @Order(1)
    private Month month;

    @Order(2)
    private Week week;

}
