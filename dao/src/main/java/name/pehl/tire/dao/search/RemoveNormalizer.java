package name.pehl.tire.dao.search;

import com.google.common.base.CharMatcher;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class RemoveNormalizer implements Normalizer
{
    private final String toRemove;


    public RemoveNormalizer(String toRemove)
    {
        this.toRemove = toRemove;
    }


    @Override
    public String normalize(String data)
    {
        String normalized = data;
        if (data != null)
        {
            normalized = CharMatcher.anyOf(toRemove).removeFrom(data);
        }
        return normalized;
    }
}
