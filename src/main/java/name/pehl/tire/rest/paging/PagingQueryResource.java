package name.pehl.taoki.paging;

import javax.ws.rs.core.MultivaluedMap;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses query
 * parameters of the resource as input. The query must contain the following
 * parameter:
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
 * <li>http://server/resource?offset=100&pageSize=50&sortField=surname
 * <li>http://server/resource?offset=1&pageSize=2&sortField=surname&sortDir=aSc
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2012-03-02 11:06:21 +0100 (Fr, 02 Mrz 2012) $ $Revision:
 *          61416 $
 */
public abstract class PagingQueryResource extends AbstractPagingResource<MultivaluedMap<String, String>>
{
    /**
     * Query parameter for 'offset'
     */
    public static final String OFFSET = "offset";

    /**
     * Query parameter for 'pageSize'
     */
    public static final String PAGE_SIZE = "pageSize";


    @Override
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
}
