package name.pehl.tire.client.model;

import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class NamedModelSuggestion<T extends NamedModel> extends MultiWordSuggestion
{
    final T model;


    public NamedModelSuggestion(String replacementString, String displayString, T model)
    {
        super(replacementString, displayString);
        this.model = model;
    }


    public T getModel()
    {
        return model;
    }
}
