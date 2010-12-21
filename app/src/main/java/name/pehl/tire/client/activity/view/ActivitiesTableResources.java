package name.pehl.tire.client.activity.view;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

public interface ActivitiesTableResources extends CellTable.Resources
{
    public interface ActivitiesTableStyle extends CellTable.Style
    {
        String activeActivity();


        String oddDays();


        String startColumn();


        String durationInHoursColumn();


        String durationFromToColumn();


        String nameColumn();


        String projectColumn();


        String actionColumn();


        String hideActions();
    }


    @Override
    @Source("activitiesTable.css")
    ActivitiesTableStyle cellTableStyle();


    ImageResource goon();


    ImageResource delete();
}
