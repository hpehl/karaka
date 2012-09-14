package name.pehl.tire.client.cell;

import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.user.cellview.client.Column;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ModelColumn<T extends BaseModel> extends Column<T, T>
{
    public ModelColumn(final ModelCell<T> cell)
    {
        super(cell);
    }


    @Override
    public T getValue(final T model)
    {
        return model;
    }
}
