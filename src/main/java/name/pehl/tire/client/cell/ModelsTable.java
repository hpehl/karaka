package name.pehl.tire.client.cell;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowStyles;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelsTable<T extends BaseModel> extends CellTable<T>
{
    // -------------------------------------------------------- private members

    protected final ModelsTableResources tableResources;
    protected ModelActionCell<T> actionCell;


    // ----------------------------------------------------------- constructors

    public ModelsTable(final ModelsTableResources tableResources)
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
                if (alternativeColor(model, rowIndex))
                {
                    return tableResources.cellTableStyle().alternativeColor();
                }
                return null;
            }
        });
    }


    // -------------------------------------------------------------- gui setup

    protected abstract void addColumns();


    /**
     * Please make sure that {@code actionCell} is assigned before this method
     * is called!
     * 
     * @param renderer
     * @param styleName
     * @param columnIndex
     * @throws IllegalStateException
     *             if {@code actionCell} is {@code null}
     */
    protected void addDataColumn(final ModelRenderer<T> renderer, final String styleName, final int columnIndex)
    {
        if (actionCell == null)
        {
            throw new IllegalStateException("actionCell is null");
        }
        ModelColumn<T> column = new ModelColumn<T>(new ModelDataCell<T>(renderer, actionCell));
        addColumnStyleName(columnIndex, styleName);
        addColumn(column);
    }


    protected boolean alternativeColor(final T model, final int rowIndex)
    {
        return rowIndex % 2 != 0;
    }


    // --------------------------------------------------------- public methods

    public void update(final List<T> models)
    {
        List<T> local = models;
        if (local == null)
        {
            local = new ArrayList<T>();
        }
        setRowData(0, local);
        setRowCount(local.size());
    }
}
