package name.pehl.tire.client.cell;

import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ModelDataCell<T extends BaseModel> extends ModelCell<T>
{
    final ModelActionCell<T> actionCell;


    public ModelDataCell(final ModelActionCell<T> actionCell, final ModelRenderer<T> renderer)
    {
        super(renderer);
        this.actionCell = actionCell;
    }


    @Override
    public void onClick(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater)
    {
        actionCell.hideActions(parent);
    }


    @Override
    public void onMouseOver(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater)
    {
        actionCell.showActions(parent);
    }


    @Override
    public void onMouseOut(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater)
    {
        actionCell.hideActions(parent);
    }
}
