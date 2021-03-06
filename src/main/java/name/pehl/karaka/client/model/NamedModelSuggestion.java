package name.pehl.karaka.client.model;

import name.pehl.karaka.shared.model.NamedModel;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class NamedModelSuggestion<T extends NamedModel> extends MultiWordSuggestion
{
    final T model;


    public NamedModelSuggestion(T model, String replacementString, String displayString)
    {
        super(replacementString, displayString);
        this.model = model;
    }


    public T getModel()
    {
        return model;
    }
}
