package name.pehl.tire.client.activity.month;

import name.pehl.tire.client.activity.Activities;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenDispatch
public class GetMonth
{
    @In(1)
    int year;

    @In(2)
    int month;

    @Out(1)
    Activities activities;
}
