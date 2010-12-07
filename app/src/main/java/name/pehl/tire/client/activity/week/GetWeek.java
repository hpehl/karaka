package name.pehl.tire.client.activity.week;

import name.pehl.tire.client.activity.Activities;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenDispatch
public class GetWeek
{
    @In(1)
    int year;

    @In(2)
    int week;

    @Out(1)
    Activities activities;
}