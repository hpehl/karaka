package name.pehl.karaka.client.cell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.shared.model.BaseModel;

import static com.google.gwt.safehtml.shared.SafeHtmlUtils.fromTrustedString;
import static com.google.gwt.user.client.ui.AbstractImagePrototype.create;

public class DeleteActionCell<T extends BaseModel> extends ModelActionCell<T>
{
    // -------------------------------------------------------------- templates

    static final Template TEMPLATE = GWT.create(Template.class);


    public DeleteActionCell(final ModelsTable<T> table, final Resources resources)
    {
        super("delete_container", table, new ModelRenderer<T>()
        {
            @Override
            public SafeHtml render(final T object)
            {
                return TEMPLATE.delete(
                        fromTrustedString(create(resources.delete())
                                .getHTML()));
            }
        });
    }


    interface Template extends SafeHtmlTemplates
    {
        @Template("<div style=\"visibility: hidden; width: 16px;\"><span id=\"delete\" title=\"Delete\">{0}</span></div>")
        SafeHtml delete(SafeHtml delete);
    }
}
