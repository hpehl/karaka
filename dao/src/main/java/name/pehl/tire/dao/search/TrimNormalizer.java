package name.pehl.tire.dao.search;

/**
 * Removes leading and trailing whitespaces, multiple whitespaces are replaced
 * by one.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
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
