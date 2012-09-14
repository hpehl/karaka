package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.BaseModel;

import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ModelTextRenderer<T extends BaseModel> extends ModelRenderer<T>
{
    @Override
    public SafeHtml render(final T model)
    {
        String value = getValue(model);
        return toSafeHtml(value);
    }


    protected abstract String getValue(T model);
}
