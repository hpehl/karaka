package name.pehl.tire.dao.normalize;

/**
 * @author $Author$
 * @version $Date$ $Revision$
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
