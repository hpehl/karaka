package name.pehl.tire.client.activity;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenDispatch
public class GetActivitiesByWeek
{
    @In(1)
    int year;

    @In(2)
    int calendarWeek;

    @Out(1)
    Week week;
}
