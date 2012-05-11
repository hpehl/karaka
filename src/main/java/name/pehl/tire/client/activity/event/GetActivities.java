package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.ActivitiesRequest;
import name.pehl.tire.shared.model.Activities;

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
