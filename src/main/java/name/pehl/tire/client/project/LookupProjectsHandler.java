package name.pehl.tire.client.project;

import name.pehl.tire.client.model.LookupNamedModelHandler;
import name.pehl.tire.shared.model.Project;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class LookupProjectsHandler extends LookupNamedModelHandler<Project>
{
    @Inject
    public LookupProjectsHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ProjectReader reader)
    {
        super(securityCookieName, securityCookieAccessor, "projects", reader);
    }
}
