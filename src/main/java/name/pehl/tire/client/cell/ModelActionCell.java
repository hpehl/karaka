package name.pehl.tire.client.cell;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasVisibility;
import name.pehl.tire.shared.model.BaseModel;

public class ModelActionCell<T extends BaseModel> extends ModelCell<T> implements HasVisibility
{
    final DivElement container;


    public ModelActionCell(final String id, final ModelsTable<T> table, final ModelRenderer<T> renderer)
    {
        super(table, renderer);
        this.container = DOM.getElementById(id).cast();
    }


    @Override
    public boolean isVisible()
    {
        return "visible".equals(container.getAttribute("visibility"));
    }


    @Override
    public void setVisible(boolean visible)
    {
        container.setAttribute("visibility", (visible ? "visible" : "hidden"));
    }


    @Override
    protected void onClick(final Context context, final Element parent, final T value,
            final NativeEvent event, final ValueUpdater<T> valueUpdater)
    {
        EventTarget eventTarget = event.getEventTarget();
        if (eventTarget != null)
        {
            ImageElement img = eventTarget.cast();
            if (ImageElement.is(img))
            {
                Element parentElement = img.getParentElement();
                if (parentElement != null && parentElement.getId() != null)
                {
                    table.onAction(value, parentElement.getId());
                }
            }
        }
    }
}
