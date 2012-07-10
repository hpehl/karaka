package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.model.FindNamedModelHandler;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class FindActivitiesHandler extends FindNamedModelHandler<Activity>
{
    @Inject
    public FindActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader reader)
    {
        super(securityCookieName, securityCookieAccessor, "activities", reader);
    }
}
