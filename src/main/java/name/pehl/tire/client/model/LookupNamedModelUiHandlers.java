package name.pehl.tire.client.model;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.gwtplatform.mvp.client.UiHandlers;

public interface LookupNamedModelUiHandlers extends UiHandlers
{
    void onRequestSuggestions(Request request, Callback callback);
}
