package name.pehl.tire.client.cell;

import name.pehl.tire.client.resources.CommonTableResources;
import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelActionCell<T extends BaseModel> extends ModelCell<T>
{
    final CommonTableResources tableResources;


    public ModelActionCell(final CommonTableResources tableResources, final ModelRenderer<T> renderer)
    {
        super(renderer);
        this.tableResources = tableResources;
    }


    @Override
    public void onMouseOver(final com.google.gwt.cell.client.Cell.Context context, final Element parent, final T value,
            final NativeEvent event, final ValueUpdater<T> valueUpdater)
    {
        DivElement actionsDiv = parent.getFirstChildElement().cast();
        showActionsDiv(actionsDiv);
    }


    @Override
    public void onMouseOut(final com.google.gwt.cell.client.Cell.Context context, final Element parent, final T value,
            final NativeEvent event, final ValueUpdater<T> valueUpdater)
    {
        DivElement actionsDiv = parent.getFirstChildElement().cast();
        hideActionsDiv(actionsDiv);
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
            actionsDiv.removeClassName(tableResources.cellTableStyle().hideActions());
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
            actionsDiv.addClassName(tableResources.cellTableStyle().hideActions());
        }
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
}
