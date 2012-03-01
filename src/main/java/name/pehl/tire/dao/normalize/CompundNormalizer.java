package name.pehl.tire.dao.normalize;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 139 $
 */
public class CompundNormalizer implements Normalizer
{
    private final Normalizer[] normalizer;


    public CompundNormalizer(Normalizer... normalizer)
    {
        this.normalizer = normalizer;
    }


    @Override
    public String normalize(String data)
    {
        String normalized = data;
        if (normalizer != null && normalizer.length != 0)
        {
            for (Normalizer n : normalizer)
            {
                normalized = n.normalize(normalized);
                if (normalized == null)
                {
                    break;
                }
            }
        }
        return normalized;
    }
}
