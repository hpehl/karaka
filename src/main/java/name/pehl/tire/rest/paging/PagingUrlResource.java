package name.pehl.taoki.paging;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses the url of
 * the resource as input. The url must contain the following template
 * parameters:
 * <ul>
 * <li><code>offset</code><br/>
 * The offset in the result.
 * <li><code>pageSize</code><br/>
 * The size of one page.
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction (optional). Must match (case insensitiv) one of the
 * constants in {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>http://server/resource/0/50
 * <li>http://server/resource/10/20/none
 * <li>http://server/resource/100/50/surname
 * <li>http://server/resource/1/2/surname/asc
 * <li>http://server/resource/1/2/zipCode/dESc
 * </ul>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-21 11:32:14 +0100 (Mi, 21 Jan 2009) $ $Revision:
 *          61416 $
 */
public abstract class PagingUrlResource extends AbstractPagingResource<UriInfo>
{
    /**
     * Path parameter for 'offset'
     */
    public static final String OFFSET = "offset";

    /**
     * Path parameter for 'pageSize'
     */
    public static final String PAGE_SIZE = "pageSize";


    @Override
    protected PageInfo getPageInfo(UriInfo input) throws PageInfoParseException
    {
        PageInfo result = null;
        if (input != null)
        {
            MultivaluedMap<String, String> parameters = input.getPathParameters();
            String offset = parameters.getFirst(OFFSET);
            String pageSize = parameters.getFirst(PAGE_SIZE);

            int offsetValue = convertInt(offset, "Paging info contains the invalid offset: \"%s\"", offset);
            int pageSizeValue = convertInt(pageSize, "Paging info contains the invalid page size: \"%s\"", pageSize);
            return new PageInfo(offsetValue, pageSizeValue);
        }
        return result;
    }
}
