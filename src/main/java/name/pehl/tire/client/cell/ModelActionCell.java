package name.pehl.tire.client.cell;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.RootPanel;
import name.pehl.tire.shared.model.BaseModel;

public class ModelActionCell<T extends BaseModel> extends ModelCell<T> implements HasVisibility
{
    final String id;


    public ModelActionCell(final String id, final ModelsTable<T> table, final ModelRenderer<T> renderer)
    {
        super(table, renderer);
        this.id = id; 
    }


    @Override
    public boolean isVisible()
    {
        return RootPanel.get(id).isVisible();
    }


    @Override
    public void setVisible(boolean visible)
    {
        RootPanel.get(id).setVisible(visible);
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
