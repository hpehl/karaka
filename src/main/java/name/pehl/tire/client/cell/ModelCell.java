package name.pehl.tire.client.cell;

import name.pehl.tire.client.model.ModelRenderer;
import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelCell<T extends BaseModel> extends AbstractSafeHtmlCell<T>
{
    final ModelsTable<T> table;


    public ModelCell(final ModelsTable<T> table, final ModelRenderer<T> renderer)
    {
        super(renderer, "click", "mouseover", "mouseout");
        this.table = table;
    }


    @Override
    public void onBrowserEvent(final Cell.Context context, final Element parent, final T value,
            final NativeEvent event, final ValueUpdater<T> valueUpdater)
    {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        if ("click".equals(event.getType()))
        {
            onClick(context, parent, value, valueUpdater);
        }
        else if ("mouseover".equals(event.getType()))
        {
            onMouseOver(context, parent, value, valueUpdater);
        }
        else if ("mouseout".equals(event.getType()))
        {
            onMouseOut(context, parent, value, valueUpdater);
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


    public abstract void onClick(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater);


    public abstract void onMouseOver(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater);


    public abstract void onMouseOut(final Cell.Context context, final Element parent, final T value,
            final ValueUpdater<T> valueUpdater);
}
