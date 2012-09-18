package name.pehl.tire.client.resources;

import name.pehl.tire.client.cell.ModelsTableStyle;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;

public interface CommonTableResources extends CellTable.Resources
{
    @Override
    @Source("cellTable.css")
    ModelsTableStyle cellTableStyle();


    ImageResource copy();


    ImageResource startStop();


    ImageResource delete();
}
