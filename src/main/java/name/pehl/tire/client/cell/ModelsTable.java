package name.pehl.tire.client.cell;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowHoverEvent;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.ui.HasVisibility;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelsTable<T extends BaseModel> extends CellTable<T> implements RowHoverEvent.Handler
{
    // -------------------------------------------------------- private members

    protected final TableResources tableResources;
    protected HasVisibility actionCell;


    // ----------------------------------------------------------- constructors

    public ModelsTable(final TableResources tableResources)
    {
        super(Integer.MAX_VALUE, tableResources, new ModelKeyProvider<T>());
        this.tableResources = tableResources;
        this.addRowHoverHandler(this);

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


    // --------------------------------------------------------- event handling

    @Override
    public void onRowHover(final RowHoverEvent event)
    {
        if (event.isUnHover())
        {
            onUnHover();
        }
        else
        {
            onHover();
        }
    }


    protected void onHover()
    {
        if (actionCell != null)
        {
            actionCell.setVisible(true);
        }
    }


    protected void onUnHover()
    {
        if (actionCell != null)
        {
            actionCell.setVisible(false);
        }
    }


    public abstract void onEdit(T model);


    public abstract void onAction(final T value, final String id);
}
