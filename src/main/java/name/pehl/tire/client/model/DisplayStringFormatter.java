package name.pehl.tire.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class DisplayStringFormatter
{
    static final String STRONG_START = "<strong>";
    static final String STRONG_END = "</strong>";

    final List<String> queries;


    public DisplayStringFormatter(String query)
    {
        this.queries = new ArrayList<String>();
        if (query != null)
        {
            this.queries.add(query);
            if (query.length() > 1)
            {
                char firstLetter = query.charAt(0);
                if (Character.isLowerCase(firstLetter))
                {
                    this.queries.add(Character.toUpperCase(firstLetter) + query.substring(1));
                }
                else
                {
                    this.queries.add(Character.toLowerCase(firstLetter) + query.substring(1));
                }
            }
            this.queries.add(query.toUpperCase());
        }
    }


    public String format(String displayString)
    {
        String result = Strings.emptyToNull(displayString);
        if (result != null)
        {
            for (String query : queries)
            {
                result = transform(result, query);
            }
        }
        return result;
    }


    private String transform(String string, String query)
    {
        String result = string;
        if (string.contains(query))
        {
            Iterable<String> parts = Splitter.on(query).split(string);
            String join = Joiner.on(STRONG_START + query + STRONG_END).join(parts);
            result = new SafeHtmlBuilder().appendHtmlConstant(join).toSafeHtml().asString();
        }
        return result;
    }
}
