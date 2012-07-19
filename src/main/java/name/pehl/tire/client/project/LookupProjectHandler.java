package name.pehl.tire.client.project;

import name.pehl.tire.client.model.LookupNamedModelHandler;
import name.pehl.tire.shared.model.Project;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class LookupProjectHandler extends LookupNamedModelHandler<Project>
{
    @Inject
    public LookupProjectHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ProjectReader reader)
    {
        super(LookupProjectAction.class, securityCookieName, securityCookieAccessor, "projects", reader);
    }
}
