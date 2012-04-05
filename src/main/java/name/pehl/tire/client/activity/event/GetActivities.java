package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.ActivitiesNavigationData;
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
    @In(1)
    ActivitiesNavigationData and;

    @Out(1)
    Activities activities;
}
