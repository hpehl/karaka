package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

public interface CellTableResources extends CellTable.Resources
{
    @Override
    @Source("loading.gif")
    ImageResource cellTableLoading();

    public interface CellTableStyle extends CellTable.Style
    {
        String activeActivity();


        String startColumn();


        String durationInHoursColumn();


        String durationFromToColumn();


        String nameColumn();


        String projectColumn();
    }


    @Override
    @Source("cellTable.css")
    CellTableStyle cellTableStyle();
}
