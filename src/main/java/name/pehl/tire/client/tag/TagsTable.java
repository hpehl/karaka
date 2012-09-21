package name.pehl.tire.client.tag;

import static name.pehl.tire.client.tag.TagAction.Action.DELETE;
import static name.pehl.tire.client.tag.TagAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.client.tag.TagActionEvent.HasTagActionHandlers;
import name.pehl.tire.client.tag.TagActionEvent.TagActionHandler;
import name.pehl.tire.shared.model.Tag;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsTable extends ModelsTable<Tag> implements HasTagActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public TagsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.tagsTableStyle().ensureInjected();
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
        column = new ModelColumn<Tag>(new TagActionCell()
        {
            @Override
            protected void onDelete(final Tag tag)
            {
                TagActionEvent.fire(TagsTable.this, DELETE, tag);
            }
        });
        addColumn(column);
        addColumnStyleName(3, resources.tagsTableStyle().actionsColumn());
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
}
