package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Activities;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class GetActivities
{
    @In(1) ActivitiesRequest activitiesRequest;
    @Out(1) Activities activities;
}
