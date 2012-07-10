package name.pehl.tire.client.model;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.gwtplatform.mvp.client.UiHandlers;

public interface FindNamedModelUiHandlers extends UiHandlers
{
    void onRequestSuggestions(String query, Request request, Callback callback);
}
