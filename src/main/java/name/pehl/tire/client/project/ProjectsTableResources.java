package name.pehl.tire.client.project;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ProjectsTableResources extends ClientBundle
{
    public interface ProjectsTableStyle extends CssResource
    {
        String nameColumn();


        String descriptionColumn();


        String clientColumn();


        String actionsColumn();
    }


    @Source("projectsTable.css")
    ProjectsTableStyle cellTableStyle();
}
