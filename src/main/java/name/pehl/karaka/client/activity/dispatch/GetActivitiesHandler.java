package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.karaka.shared.model.Link;
import name.pehl.karaka.shared.model.LinksParser;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.activity.model.ActivitiesReader;
import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.karaka.shared.model.Activities;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import java.util.List;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetActivitiesHandler extends KarakaActionHandler<GetActivitiesAction, GetActivitiesResult>
{
    private final ActivitiesReader activitiesReader;


    @Inject
    protected GetActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetActivitiesAction.class, securityCookieName, securityCookieAccessor);
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
        method.send(new KarakaJsonCallback<Activities, GetActivitiesResult>(activitiesReader, resultCallback)
        {
            @Override
            protected GetActivitiesResult extractResult(final Method method, JsonReader<Activities> reader, JSONObject json)
            {
                String linkHeader = method.getResponse().getHeader("Link");
                List<Link> links = LinksParser.valueOf(linkHeader);
                Activities activities = activitiesReader.read(json);
                activities.setLinks(links);
                return new GetActivitiesResult(activities);
            }
        });
    }
}
