package name.pehl.tire.client.cell;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

public interface ModelsTableResources extends CellTable.Resources
{
    @Override
    @Source("modelsTable.css")
    ModelsTableStyle cellTableStyle();


    ImageResource copy();


    ImageResource startStop();


    ImageResource delete();
}
