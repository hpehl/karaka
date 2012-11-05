package name.pehl.karaka.client.activity.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import name.pehl.karaka.client.cell.ModelActionCell;
import name.pehl.karaka.client.cell.ModelRenderer;
import name.pehl.karaka.client.cell.ModelsTable;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.shared.model.Activity;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromTrustedString;
import static com.google.gwt.user.client.ui.AbstractImagePrototype.create;
import static name.pehl.karaka.client.activity.event.ActivityAction.Action;
import static name.pehl.karaka.client.activity.event.ActivityAction.Action.COPY;
import static name.pehl.karaka.client.activity.event.ActivityAction.Action.DELETE;
import static name.pehl.karaka.client.activity.event.ActivityAction.Action.START_STOP;

public class ActivityActionCell extends ModelActionCell<Activity>
{
    interface Template extends SafeHtmlTemplates
    {
        @Template("<div style=\"visibility: hidden; width: 56px;\">" +
                "<span id=\"{0}\" style=\"margin-right:4px;\" title=\"Copy and add one day\">{1}</span>" +
                "<span id=\"{2}\" style=\"margin-right:4px;\" title=\"Continue\">{3}</span>" +
                "<span id=\"{4}\" title=\"Delete\">{5}</span></div>")
        SafeHtml actions(Action copyAction, SafeHtml copy,
                Action startStopAction, SafeHtml startStop,
                Action deleAction, SafeHtml delete);
    }

    static final Template TEMPLATE = GWT.create(Template.class);

    public ActivityActionCell(final ModelsTable<Activity> table, final Resources resources)
    {
        super("actions_container", table, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity object)
            {
                return TEMPLATE.actions(COPY, fromTrustedString(create(resources.copy()).getHTML()),
                        START_STOP, fromTrustedString(create(resources.startStop()).getHTML()),
                        DELETE, fromTrustedString(create(resources.delete()).getHTML()));
            }
        });
    }
}
