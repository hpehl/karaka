package name.pehl.tire.dao.normalize;

/**
 * @author $Author$
 * @version $Date$ $Revision$
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
