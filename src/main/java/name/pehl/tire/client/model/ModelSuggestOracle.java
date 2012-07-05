package name.pehl.tire.client.model;

import java.util.Collection;

import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ModelSuggestOracle<T extends NamedModel> extends SuggestOracle
{
    @Override
    public void requestSuggestions(Request request, Callback callback)
    {
        Response resp = new Response(matchingModels(request.getQuery(), request.getLimit()));
        callback.onSuggestionsReady(request, resp);
    }


    public Collection<ModelMultiWordSuggestion<T>> matchingModels(String query, int limit)
    {
        return null;
    }
}
