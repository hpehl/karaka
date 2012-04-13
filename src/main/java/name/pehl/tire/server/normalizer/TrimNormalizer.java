package name.pehl.tire.server.normalizer;

/**
 * Removes leading and trailing whitespaces, multiple whitespaces are replaced
 * by one.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 139
 *          $
 */
public class TrimNormalizer implements Normalizer
{
    @Override
    public String normalize(String data)
    {
        String normalized = data;
        if (data != null)
        {
            normalized = data.trim();
            normalized = normalized.replaceAll("\\s+", " ");
        }
        return normalized;
    }
}
