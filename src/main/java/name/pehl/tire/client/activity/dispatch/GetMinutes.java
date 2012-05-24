package name.pehl.tire.client.activity.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetMinutes
{
    @In(1) ActivitiesRequest activitiesRequest;
    @Out(1) int minutes;
}
