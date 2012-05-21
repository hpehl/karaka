package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.shared.model.Activities;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetActivitiesHandler extends TireActionHandler<Activities, GetActivitiesAction, GetActivitiesResult>
{
    @Inject
    protected GetActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetActivitiesAction.class, securityCookieName, securityCookieAccessor, activitiesReader);
    }


    @Override
    protected Method getMethod(GetActivitiesAction action)
    {
        return new Resource(action.getActivitiesRequest().toUrl()).get();
    }


    @Override
    protected GetActivitiesResult extractResult(JSONObject jsonObject)
    {
        return new GetActivitiesResult(jsonReader.read(jsonObject));
    }
}
