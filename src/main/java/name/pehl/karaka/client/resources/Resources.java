package name.pehl.karaka.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 157
 *          $
 */
public interface Resources extends ClientBundle
{
    // ----------------------------------------------------------------- images

    ImageResource copy();
    ImageResource delete();
    ImageResource recordOn();
    ImageResource recordOff();
    ImageResource selectedNavigation();
    ImageResource startStop();
    ImageResource stopwatch32();
    ImageResource stopwatch64();
    ImageResource stopwatch128();

    // -------------------------------------------------------------------- CSS

    /**
     * Global CSS rules
     * 
     * @return
     */
    @Source("karaka.css")
    CssResource karaka();


    /**
     * CSS classes to style GWT and custom widgets
     * 
     * @return
     */
    @Source("widgets.css")
    CssResource widgets();


    public interface Message extends CssResource
    {
        String show();
        String hide();
    }
    @Source("message.css")
    Message message();


    public interface Navigation extends CssResource
    {
        String selectedNavigationEntry();
    }
    @Source("navigation.css")
    Navigation navigation();


    public interface ActivitiesTableStyle extends CssResource
    {
        String activeActivity();
        String startColumn();
        String durationInHoursColumn();
        String durationFromToColumn();
        String nameColumn();
        String projectColumn();
        String actionsColumn();
    }
    @Source("activitiesTable.css")
    ActivitiesTableStyle activitiesTableStyle();


    public interface ClientsTableStyle extends CssResource
    {
        String nameColumn();
        String descriptionColumn();
        String actionsColumn();
    }
    @Source("clientsTable.css")
    ClientsTableStyle clientsTableStyle();


    public interface ProjectsTableStyle extends CssResource
    {
        String nameColumn();
        String descriptionColumn();
        String clientColumn();
        String actionsColumn();
    }
    @Source("projectsTable.css")
    ProjectsTableStyle projectsTableStyle();


    public interface TagsTableStyle extends CssResource
    {
        String nameColumn();
        String actionsColumn();
    }
    @Source("tagsTable.css")
    TagsTableStyle tagsTableStyle();
}
