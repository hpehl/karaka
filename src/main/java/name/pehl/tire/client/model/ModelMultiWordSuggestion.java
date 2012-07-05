package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ModelMultiWordSuggestion<T extends NamedModel> extends MultiWordSuggestion
{
    final T model;


    public ModelMultiWordSuggestion(T model)
    {
        super(model.getName(), model.getName());
        this.model = model;
    }


    public T getModel()
    {
        return model;
    }
}
