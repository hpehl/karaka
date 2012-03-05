package name.pehl.tire.server.dao.normalize;

import com.google.common.base.CharMatcher;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 139 $
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
