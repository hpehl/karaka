package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.shared.model.NamedModel;

import com.google.gwt.user.client.ui.SuggestOracle;

public class NamedModelSuggestOracle<T extends NamedModel> extends SuggestOracle
{
    protected final ModelCache<T> modelCache;


    public NamedModelSuggestOracle(ModelCache<T> modelCache)
    {
        super();
        this.modelCache = modelCache;
    }


    @Override
    public void requestSuggestions(Request request, Callback callback)
    {
        if (!modelCache.isEmpty())
        {
            String query = request.getQuery().trim();
            if (query != null && query.length() != 0)
            {
                List<NamedModelSuggestion<T>> suggestions = filter(query);
                callback.onSuggestionsReady(request, new Response(suggestions));
            }
        }
    }


    protected List<NamedModelSuggestion<T>> filter(String query)
    {
        Highlighter highlighter = new Highlighter(query);
        List<NamedModelSuggestion<T>> suggestions = new ArrayList<NamedModelSuggestion<T>>();
        for (T model : modelCache)
        {
            if (model.getName().toLowerCase().contains(query.toLowerCase()))
            {
                NamedModelSuggestion<T> suggestion = new NamedModelSuggestion<T>(model, model.getName(),
                        highlighter.highlight(model.getName()));
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }


    @Override
    public boolean isDisplayStringHTML()
    {
        return true;
    }
}
