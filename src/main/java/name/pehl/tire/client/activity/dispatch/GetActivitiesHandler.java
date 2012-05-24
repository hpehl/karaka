package name.pehl.tire.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.shared.model.Activities;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import static name.pehl.tire.client.dispatch.TireActionHandler.HttpMethod.GET;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetActivitiesHandler extends TireActionHandler<GetActivitiesAction, GetActivitiesResult>
{
    private final ActivitiesReader activitiesReader;


    @Inject
    protected GetActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetActivitiesAction.class, GET, CONTENT_TYPE_JSON, securityCookieName, securityCookieAccessor);
        this.activitiesReader = activitiesReader;
    }


    @Override
    protected Resource resourceFor(GetActivitiesAction action)
    {
        return new Resource(action.getActivitiesRequest().toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetActivitiesResult> resultCallback)
    {
        method.send(new TireJsonCallback<Activities, GetActivitiesResult>(activitiesReader, resultCallback)
        {
            @Override
            protected GetActivitiesResult extractResult(JsonReader<Activities> reader, JSONObject json)
            {
                return new GetActivitiesResult(activitiesReader.read(json));
            }
        });
    }
}
