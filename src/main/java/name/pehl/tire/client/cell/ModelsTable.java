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

    final ModelsTableResources tableResources;
    final ModelActionCell<T> actionCell;


    // ----------------------------------------------------------- constructors

    public ModelsTable(final ModelsTableResources tableResources, final ModelActionCell<T> actionCell,
            final int actionColumnIndex)
    {
        super(Integer.MAX_VALUE, tableResources, new ModelKeyProvider<T>());
        this.tableResources = tableResources;
        this.actionCell = actionCell;

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
        addDataColumns();
        addActionColumn(actionColumnIndex);
    }


    // -------------------------------------------------------------- gui setup

    protected abstract void addDataColumns();


    protected void addActionColumn(final int columnIndex)
    {
        ModelColumn<T> actionColumn = new ModelColumn<T>(actionCell);
        addColumnStyleName(columnIndex, tableResources.cellTableStyle().actionsColumn());
        addColumn(actionColumn);
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


    public abstract void onAction(T model);
}
