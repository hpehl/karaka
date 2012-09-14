package name.pehl.tire.client.project;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

public interface ProjectsTableResources extends CellTable.Resources
{
    public interface ProjectsTableStyle extends CellTable.Style
    {
        String odd();


        String nameColumn();


        String descriptionColumn();


        String clientColumn();


        String actionsColumn();


        String hideActions();
    }


    @Override
    @Source("projectsTable.css")
    ProjectsTableStyle cellTableStyle();


    ImageResource delete();
}
