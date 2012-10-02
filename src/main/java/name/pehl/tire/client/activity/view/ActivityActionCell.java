package name.pehl.tire.client.activity.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.shared.model.Activity;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromTrustedString;
import static com.google.gwt.user.client.ui.AbstractImagePrototype.create;

public class ActivityActionCell extends ModelActionCell<Activity>
{
    interface Template extends SafeHtmlTemplates
    {
        @Template(
                "<div id=\"actions_container\" style=\"width: 56px;\"><span id=\"copy\" style=\"margin-right:4px;\" " +
                        "title=\"Copy and add one " +
                        "day\">{0}</span><span id=\"start_stop\" style=\"margin-right:4px;\" " +
                        "title=\"Continue\">{1}</span><span id=\"delete\" title=\"Delete\">{2}</span></div>")
        SafeHtml actions(SafeHtml copy, SafeHtml goon, SafeHtml delete);
    }

    static final Template TEMPLATE = GWT.create(Template.class);


    public ActivityActionCell(final ModelsTable<Activity> table, final Resources resources)
    {
        super("actions_container", table, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity object)
            {
                return TEMPLATE.actions(fromTrustedString(create(resources.copy())
                        .getHTML()), fromTrustedString(create(
                        resources.startStop()).getHTML()),
                        fromTrustedString(create(resources.delete())
                                .getHTML()));
            }
        });
    }
}
