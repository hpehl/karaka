package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesNavigationData;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

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
