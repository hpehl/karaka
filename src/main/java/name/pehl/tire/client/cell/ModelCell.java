package name.pehl.tire.client.cell;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import name.pehl.tire.shared.model.BaseModel;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ModelCell<T extends BaseModel> extends AbstractSafeHtmlCell<T>
{
    protected final ModelsTable<T> table;


    public ModelCell(final ModelsTable<T> table, final ModelRenderer<T> renderer)
    {
        super(renderer, "click");
        this.table = table;
    }


    @Override
    public void onBrowserEvent(final Cell.Context context, final Element parent, final T value,
            final NativeEvent event, final ValueUpdater<T> valueUpdater)
    {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        if ("click".equals(event.getType()))
        {
            onClick(context, parent, value, event, valueUpdater);
        }
    }


    @Override
    protected void render(final Cell.Context context, final SafeHtml data, final SafeHtmlBuilder sb)
    {
        if (data != null)
        {
            sb.append(data);
        }
    }


    protected void onClick(final Cell.Context context, final Element parent, final T value, final NativeEvent event,
            final ValueUpdater<T> valueUpdater)
    {
        table.onEdit(value);
    }
}
