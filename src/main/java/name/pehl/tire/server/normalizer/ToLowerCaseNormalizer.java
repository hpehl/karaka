package name.pehl.tire.server.normalizer;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 139
 *          $
 */
public class ToLowerCaseNormalizer implements Normalizer
{
    @Override
    public String normalize(String data)
    {
        String normalized = data;
        if (data != null)
        {
            normalized = data.toLowerCase();
        }
        return normalized;
    }
}
