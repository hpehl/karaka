package name.pehl.tire.client.resources;


import com.google.gwt.user.cellview.client.CellTable;

public interface TableResources extends CellTable.Resources
{
    @Override
    @Source("cellTable.css")
    TableStyle cellTableStyle();
}
