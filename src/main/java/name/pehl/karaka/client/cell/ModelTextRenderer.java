package name.pehl.karaka.client.cell;

import name.pehl.karaka.shared.model.BaseModel;

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
