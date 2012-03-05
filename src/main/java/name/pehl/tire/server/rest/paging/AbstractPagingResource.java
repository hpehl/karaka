package name.pehl.tire.server.rest.paging;

/**
 * Abstract paging resource. This class must return a {@link PageInfo} instance
 * for the specified input.
 * 
 * @param <T>
 *            The input type
 * @author $Author: harald.pehl $
 * @version $Date: 2012-03-02 11:06:21 +0100 (Fr, 02 Mrz 2012) $ $Revision: 97
 *          $
 */
public abstract class AbstractPagingResource<T>
{
    /**
     * Responsible for returning a {@link PageInfo} instance for the specified
     * input.
     */
    protected abstract PageInfo getPageInfo(T input) throws PageInfoParseException;


    protected int convertInt(String input, String message, Object... params) throws PageInfoParseException
    {
        try
        {
            int result = Integer.parseInt(input);
            return result;
        }
        catch (NumberFormatException e)
        {
            String error = String.format(message, params);
            throw new PageInfoParseException(error);
        }
    }
}
