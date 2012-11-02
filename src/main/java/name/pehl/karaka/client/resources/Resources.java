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

    // @formatter:off
    ImageResource copy();
    ImageResource delete();
    ImageResource recordOn();
    ImageResource recordOff();
    ImageResource selectedNavigation();
    ImageResource startStop();
    ImageResource stopwatch32();
    ImageResource stopwatch64();
    ImageResource stopwatch128();
    // @formatter:on

    // -------------------------------------------------------------------- CSS

    /**
     * Global CSS rules
     * 
     * @return
     */
    @Source("tire.css")
    CssResource tire();


    /**
     * CSS classes to style GWT and custom widgets
     * 
     * @return
     */
    @Source("widgets.css")
    CssResource widgets();

    public interface Message extends CssResource
    {
        // @formatter:off
        String show();
        String hide();
        // @formatter:on
    }


    @Source("message.css")
    Message message();

    public interface Navigation extends CssResource
    {
        // @formatter:off
        String selectedNavigationEntry();
        String selectedDate();
        // @formatter:on
    }


    @Source("navigation.css")
    Navigation navigation();

    public interface ActivitiesTableStyle extends CssResource
    {
        // @formatter:off
        String activeActivity();
        String startColumn();
        String durationInHoursColumn();
        String durationFromToColumn();
        String nameColumn();
        String projectColumn();
        String actionsColumn();
        // @formatter:on
    }


    @Source("activitiesTable.css")
    ActivitiesTableStyle activitiesTableStyle();

    public interface ClientsTableStyle extends CssResource
    {
        // @formatter:off
        String nameColumn();
        String descriptionColumn();
        String actionsColumn();
        // @formatter:on
    }


    @Source("clientsTable.css")
    ClientsTableStyle clientsTableStyle();

    public interface ProjectsTableStyle extends CssResource
    {
        // @formatter:off
        String nameColumn();
        String descriptionColumn();
        String clientColumn();
        String actionsColumn();
        // @formatter:on
    }


    @Source("projectsTable.css")
    ProjectsTableStyle projectsTableStyle();

    public interface TagsTableStyle extends CssResource
    {
        // @formatter:off
        String nameColumn();
        String actionsColumn();
        // @formatter:on
    }


    @Source("tagsTable.css")
    TagsTableStyle tagsTableStyle();
}
