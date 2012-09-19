package name.pehl.tire.client.tag;

import static name.pehl.tire.client.tag.TagAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.client.tag.TagAction.Action;
import name.pehl.tire.client.tag.TagActionEvent.HasTagActionHandlers;
import name.pehl.tire.client.tag.TagActionEvent.TagActionHandler;
import name.pehl.tire.shared.model.Tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsTable extends ModelsTable<Tag> implements HasTagActionHandlers
{
    // -------------------------------------------------------------- templates

    interface ActionsTemplates extends SafeHtmlTemplates
    {
        @Template("<div class=\"{0}\" style=\"width: 16px;\"><span id=\"delete\" title=\"Delete\">{1}</span></div>")
        SafeHtml actions(String hideActionsClassname, SafeHtml delete);
    }

    static final ActionsTemplates TEMPLATES = GWT.create(ActionsTemplates.class);

    // ---------------------------------------------------------------- members

    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public TagsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.projectsTableStyle().ensureInjected();
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Action is the last column in the UI, but the first one to create!
        this.actionCell = new ModelActionCell<Tag>(this, new ModelRenderer<Tag>()
        {
            @Override
            public SafeHtml render(final Tag tag)
            {
                SafeHtml deleteHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resources.delete())
                        .getHTML());
                return TEMPLATES.actions(tableResources.cellTableStyle().hideActions(), deleteHtml);
            }
        });

        // Column #0: Name
        addDataColumn(resources.projectsTableStyle().nameColumn(), 0, new ModelTextRenderer<Tag>()
        {
            @Override
            protected String getValue(final Tag tag)
            {
                return tag.getName();
            }
        }, null, null);

        // Column #1: Action
        ModelColumn<Tag> actionColumn = new ModelColumn<Tag>(actionCell);
        addColumnStyleName(1, resources.projectsTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addTagActionHandler(final TagActionHandler handler)
    {
        return addHandler(handler, TagActionEvent.getType());
    }


    @Override
    protected void onClick(final Tag tag)
    {
        TagActionEvent.fire(this, DETAILS, tag);
    }


    @Override
    protected void onAction(final Tag tag, final String actionId)
    {
        TagActionEvent.fire(this, Action.valueOf(actionId.toUpperCase()), tag);
    }
}
