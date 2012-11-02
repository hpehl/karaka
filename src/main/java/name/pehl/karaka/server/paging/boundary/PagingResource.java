package name.pehl.karaka.server.paging.boundary;

import javax.ws.rs.core.MultivaluedMap;

import name.pehl.karaka.server.paging.entity.PageInfo;

/**
 * A paging resource which uses query parameters of the resource as input. The
 * query must contain the following parameter:
 * <ul>
 * <li><code>offset</code><br/>
 * The offset in the result.
 * <li><code>pageSize</code><br/>
 * The size of one page.
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction. Must match (case insensitiv) one of the constants in
 * {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>http://server/resource?offset=0&pageSize=50
 * <li>http://server/resource?offset=100&pageSize=100
 * </ul>
 * 
 * @param <T>
 *            The input type
 * @author $Author: harald.pehl $
 * @version $Date: 2012-03-02 11:06:21 +0100 (Fr, 02 Mrz 2012) $ $Revision: 97 $
 */
public abstract class PagingResource<T>
{
    /**
     * Query parameter for 'offset'
     */
    public static final String OFFSET = "offset";

    /**
     * Query parameter for 'pageSize'
     */
    public static final String PAGE_SIZE = "pageSize";


    protected PageInfo getPageInfo(MultivaluedMap<String, String> input) throws PageInfoParseException
    {
        PageInfo result = null;
        if (input != null)
        {
            String offset = input.getFirst(OFFSET);
            String pageSize = input.getFirst(PAGE_SIZE);
            int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
            int pageSizeValue = convertInt(pageSize, "Paging info contains the invalid page size: \"%s\"", pageSize);
            result = new PageInfo(offsetValue, pageSizeValue);
        }
        return result;
    }


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
