package name.pehl.karaka.client.tag;

import com.google.gwt.event.shared.HandlerRegistration;
import name.pehl.karaka.client.cell.*;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.client.tag.TagActionEvent.HasTagActionHandlers;
import name.pehl.karaka.client.tag.TagActionEvent.TagActionHandler;
import name.pehl.karaka.shared.model.Tag;

import static name.pehl.karaka.client.tag.TagAction.Action.DELETE;
import static name.pehl.karaka.client.tag.TagAction.Action.DETAILS;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsTable extends ModelsTable<Tag> implements HasTagActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.karaka.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public TagsTable(final name.pehl.karaka.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.tagsTableStyle().ensureInjected();
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Column #0: Name
        ModelColumn<Tag> column = new ModelColumn<Tag>(new ModelCell<Tag>(this, new ModelTextRenderer<Tag>()
        {
            @Override
            protected String getValue(final Tag tag)
            {
                return tag.getName();
            }
        }));
        addColumn(column);
        addColumnStyleName(0, resources.tagsTableStyle().nameColumn());

        // Column #1: Action
        DeleteActionCell<Tag> actionCell = new DeleteActionCell<Tag>(this, resources);
        column = new ModelColumn<Tag>(actionCell);
        addColumn(column);
        addColumnStyleName(3, resources.tagsTableStyle().actionsColumn());
        this.actionCell = actionCell;
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addTagActionHandler(final TagActionHandler handler)
    {
        return addHandler(handler, TagActionEvent.getType());
    }


    @Override
    public void onEdit(final Tag tag)
    {
        TagActionEvent.fire(this, DETAILS, tag);
    }


    @Override
    public void onAction(final Tag tag, final String id)
    {
        TagActionEvent.fire(this, DELETE, tag);
    }
}
