package name.pehl.karaka.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class Highlighter
{
    static final String STRONG_START = "<strong>";
    static final String STRONG_END = "</strong>";

    final List<String> textVariants;


    public Highlighter(String text)
    {
        this.textVariants = new ArrayList<String>();
        if (text != null)
        {
            this.textVariants.add(text);
            if (text.length() > 1)
            {
                char firstLetter = text.charAt(0);
                if (Character.isLowerCase(firstLetter))
                {
                    this.textVariants.add(Character.toUpperCase(firstLetter) + text.substring(1));
                }
                else
                {
                    this.textVariants.add(Character.toLowerCase(firstLetter) + text.substring(1));
                }
            }
            this.textVariants.add(text.toUpperCase());
        }
    }


    public String highlight(String value)
    {
        String result = Strings.emptyToNull(value);
        if (result != null)
        {
            for (String text : textVariants)
            {
                result = transform(result, text);
            }
        }
        return result;
    }


    private String transform(String value, String text)
    {
        String result = value;
        if (value.contains(text))
        {
            Iterable<String> parts = Splitter.on(text).split(value);
            String join = Joiner.on(STRONG_START + text + STRONG_END).join(parts);
            result = new SafeHtmlBuilder().appendHtmlConstant(join).toSafeHtml().asString();
        }
        return result;
    }
}
