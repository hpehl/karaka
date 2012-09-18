package name.pehl.tire.client.activity.view;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.COPY;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.DELETE;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.resources.CommonTableResources;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityActionCell extends ModelActionCell<Activity>
{
    private final ActivitiesTable table;


    public ActivityActionCell(final ActivitiesTable table, final CommonTableResources tableResources,
            final ModelRenderer<Activity> renderer)
    {
        super(tableResources, renderer);
        this.table = table;
    }


    @Override
    public void onClick(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Activity value, final NativeEvent event, final ValueUpdater<Activity> valueUpdater)
    {
        EventTarget eventTarget = event.getEventTarget();
        if (eventTarget != null)
        {
            ImageElement img = eventTarget.cast();
            if (ImageElement.is(img))
            {
                ImageElement copy = findImage(parent, 0);
                ImageElement startStop = findImage(parent, 1);
                ImageElement delete = findImage(parent, 2);
                if (img == copy)
                {
                    table.onActivityAction(COPY, value);
                }
                else if (img == startStop)
                {
                    table.onActivityAction(START_STOP, value);
                }
                else if (img == delete)
                {
                    table.onActivityAction(DELETE, value);
                }
            }
        }
    }


    private ImageElement findImage(final Element div, final int index)
    {
        Node node = null;
        Element element = null;
        ImageElement img = null;
        if (div != null)
        {
            element = div.getFirstChildElement();
            if (element != null)
            {
                // This element contains the images inside <span> tags
                int chidlren = element.getChildCount();
                if (index < chidlren)
                {
                    node = element.getChild(index);
                    if (node != null && node instanceof Element)
                    {
                        node = node.getFirstChild();
                        if (node != null && ImageElement.is(node))
                        {
                            img = node.cast();
                        }
                    }
                }
            }
        }
        return img;
    }
}
