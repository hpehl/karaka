package name.pehl.karaka.client.cell;

import name.pehl.karaka.shared.model.BaseModel;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ModelColumn<T extends BaseModel> extends Column<T, T>
{
    public ModelColumn(final Cell<T> cell)
    {
        super(cell);
    }


    @Override
    public T getValue(final T model)
    {
        return model;
    }
}
