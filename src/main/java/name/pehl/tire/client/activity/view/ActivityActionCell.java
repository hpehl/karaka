package name.pehl.tire.client.activity.view;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.COPY;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.DELETE;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityActionCell extends AbstractSafeHtmlCell<Activity>
{
    private final ActivitiesTable activitiesTable;
    private final ActivitiesTableResources atr;


    public ActivityActionCell(final ActivitiesTable activitiesTable, final ActivitiesTableResources atr)
    {
        super(new ActionRenderer(atr), "click", "mouseover", "mouseout");
        this.activitiesTable = activitiesTable;
        this.atr = atr;
    }


    @Override
    public void onBrowserEvent(final Cell.Context context, final Element parent, final Activity value,
            final NativeEvent event, final ValueUpdater<Activity> valueUpdater)
    {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        if ("click".equals(event.getType()))
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
                        activitiesTable.onActivityAction(COPY, value);
                    }
                    else if (img == startStop)
                    {
                        activitiesTable.onActivityAction(START_STOP, value);
                    }
                    else if (img == delete)
                    {
                        activitiesTable.onActivityAction(DELETE, value);
                    }
                }
            }
        }
        else if ("mouseover".equals(event.getType()))
        {
            DivElement actionsDiv = parent.getFirstChildElement().cast();
            showActionsDiv(actionsDiv);
        }
        else if ("mouseout".equals(event.getType()))
        {
            DivElement actionsDiv = parent.getFirstChildElement().cast();
            hideActionsDiv(actionsDiv);
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


    /**
     * Shows the actions.
     * 
     * @param cellDiv
     *            The div element of the calling cell
     */
    public void showActions(final Element cellDiv)
    {
        DivElement actionsDiv = findActionsDiv(cellDiv);
        showActionsDiv(actionsDiv);
    }


    private void showActionsDiv(final DivElement actionsDiv)
    {
        if (actionsDiv != null)
        {
            actionsDiv.removeClassName(atr.cellTableStyle().hideActions());
        }
    }


    /**
     * Hides the actions.
     * 
     * @param cellDiv
     *            The div element of the calling cell
     */
    public void hideActions(final Element cellDiv)
    {
        DivElement actionsDiv = findActionsDiv(cellDiv);
        hideActionsDiv(actionsDiv);
    }


    private void hideActionsDiv(final DivElement actionsDiv)
    {
        if (actionsDiv != null)
        {
            actionsDiv.addClassName(atr.cellTableStyle().hideActions());
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


    /**
     * Finds the actions div starting from the specified cell div. The path is
     * <code>cellDiv.getParentElement().getParentElement().getLastChild().getFirstChild().getFirstChild().cast()</code>
     * 
     * @param cellDiv
     *            the calling cell div
     * @return the actions div
     */
    private DivElement findActionsDiv(final Element cellDiv)
    {
        Node node = null;
        Element element = null;
        DivElement actionsDiv = null;
        if (cellDiv != null)
        {
            element = cellDiv.getParentElement();
            if (element != null)
            {
                element = element.getParentElement();
                if (element != null)
                {
                    node = element.getLastChild();
                    if (node != null && node instanceof Element)
                    {
                        element = ((Element) node).getFirstChildElement();
                        if (element != null)
                        {
                            element = element.getFirstChildElement();
                            if (element != null && DivElement.is(element))
                            {
                                actionsDiv = element.cast();
                            }
                        }
                    }
                }
            }
        }
        return actionsDiv;
    }

    // ---------------------------------------------------------- inner classes

    static class ActionRenderer extends ModelRenderer<Activity>
    {
        private final ActivitiesTableResources atr;


        ActionRenderer(final ActivitiesTableResources atr)
        {
            this.atr = atr;
        }


        @Override
        public SafeHtml render(final Activity object)
        {
            SafeHtml copyHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(atr.copy()).getHTML());
            SafeHtml startStopHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(atr.startStop())
                    .getHTML());
            SafeHtml deleteHtml = SafeHtmlUtils
                    .fromTrustedString(AbstractImagePrototype.create(atr.delete()).getHTML());
            return ActivityTemplates.INSTANCE.actions(atr.cellTableStyle().hideActions(), copyHtml, startStopHtml,
                    deleteHtml);
        }
    }
}
