package name.pehl.tire.client.project;

import name.pehl.gwt.taoki.client.rest.ModelsCallback;
import name.pehl.gwt.taoki.client.rest.RestCallback;
import name.pehl.gwt.taoki.client.rest.RestClient;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectClient extends RestClient
{
    public void add(Project project, RestCallback callback)
    {
    }


    public void list(ModelsCallback<Project> callback)
    {
        doGet(newUrlBuilder().buildUrl(), callback);
    }
}
