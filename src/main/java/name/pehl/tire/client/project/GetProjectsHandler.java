package name.pehl.tire.client.project;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Project;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class GetProjectsHandler extends TireActionHandler<GetProjectsAction, GetProjectsResult>
{
    final ProjectReader projectReader;


    @Inject
    protected GetProjectsHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ProjectReader projectReader)
    {
        super(GetProjectsAction.class, securityCookieName, securityCookieAccessor);
        this.projectReader = projectReader;
    }


    @Override
    protected Resource resourceFor(GetProjectsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("projects").toUrl());
    }


    @Override
    protected void executeMethod(Method method, AsyncCallback<GetProjectsResult> resultCallback)
    {
        method.send(new TireJsonCallback<Project, GetProjectsResult>(projectReader, resultCallback)
        {
            @Override
            protected GetProjectsResult extractResult(JsonReader<Project> reader, JSONObject json)
            {
                return new GetProjectsResult(reader.readList(json));
            }
        });
    }
}
