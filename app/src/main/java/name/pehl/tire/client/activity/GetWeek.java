package name.pehl.tire.client.activity;

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
    int weekNumber;

    @Out(1)
    Week week;
}
