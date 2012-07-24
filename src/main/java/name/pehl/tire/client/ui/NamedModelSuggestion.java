package name.pehl.tire.client.ui;

import name.pehl.tire.shared.model.NamedModel;

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
