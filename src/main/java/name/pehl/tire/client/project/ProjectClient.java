package name.pehl.tire.client.project;

import java.util.List;

import name.pehl.tire.client.rest.UrlBuilder;

import org.restlet.client.data.MediaType;
import org.restlet.client.data.Preference;
import org.restlet.client.resource.Result;

import com.google.inject.Inject;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectClient
{
    private final ProjectClientProxy proxy;


    @Inject
    public ProjectClient(ProjectClientProxy proxy)
    {
        this.proxy = proxy;
    }


    public void list(Result<List<Project>> callback)
    {
        UrlBuilder urlBuilder = new UrlBuilder();
        proxy.getClientResource().setReference(urlBuilder.buildUrl());
        proxy.getClientResource().getClientInfo().getAcceptedMediaTypes().add(
                new Preference<MediaType>(MediaType.APPLICATION_JAVA_OBJECT_GWT));
        proxy.list(callback);
    }
}
