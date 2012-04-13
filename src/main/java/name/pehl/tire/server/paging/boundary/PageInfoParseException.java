package name.pehl.tire.server.paging.boundary;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2012-03-02 11:06:21 +0100 (Fr, 02 Mrz 2012) $ $Revision: 216
 *          $
 */
public class PageInfoParseException extends Exception
{
    public PageInfoParseException()
    {
    }


    public PageInfoParseException(String message)
    {
        super(message);
    }


    public PageInfoParseException(Throwable cause)
    {
        super(cause);
    }


    public PageInfoParseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
