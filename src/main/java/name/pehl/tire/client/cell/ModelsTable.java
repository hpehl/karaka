package name.pehl.tire.client.cell;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.resources.CommonTableResources;
import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelsTable<T extends BaseModel> extends CellTable<T>
{
    // -------------------------------------------------------- private members

    protected final CommonTableResources tableResources;
    protected ModelActionCell<T> actionCell;


    // ----------------------------------------------------------- constructors

    public ModelsTable(final CommonTableResources tableResources)
    {
        super(Integer.MAX_VALUE, tableResources, new ModelKeyProvider<T>());
        this.tableResources = tableResources;

        setRowCount(0);
        setKeyboardSelectionPolicy(DISABLED);
        setRowStyles(new RowStyles<T>()
        {
            @Override
            public String getStyleNames(final T model, final int rowIndex)
            {
                return rowStyle(model, rowIndex);
            }
        });
    }


    // -------------------------------------------------------------- gui setup

    protected abstract void addColumns();


    /**
     * Please make sure that {@code actionCell} is assigned before this method
     * is called!
     * 
     * @param styleName
     * @param columnIndex
     * @param renderer
     * @throws IllegalStateException
     *             if {@code actionCell} is {@code null}
     */
    protected ModelColumn<T> addDataColumn(final String styleName, final int columnIndex,
            final ModelRenderer<T> renderer, final Header<?> header, final Header<?> footer)
    {
        if (actionCell == null)
        {
            throw new IllegalStateException("actionCell is null");
        }
        ModelColumn<T> column = new ModelColumn<T>(new ModelDataCell<T>(actionCell, renderer));
        addColumn(column, header, footer);
        addColumnStyleName(columnIndex, styleName);
        return column;
    }


    protected String rowStyle(final T model, final int rowIndex)
    {
        return rowIndex % 2 != 0 ? tableResources.cellTableStyle().alternativeColor() : null;
    }


    // --------------------------------------------------------- public methods

    public void update(final List<T> models)
    {
        List<T> local = models;
        if (local == null)
        {
            local = new ArrayList<T>();
        }
        setRowData(local);
    }
}
