package name.pehl.taoki.paging;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.HttpHeaders;

/**
 * A {@linkplain AbstractPagingResource paging resource} which uses the custom
 * header <code>Item-Range</code> as input. The page info is expected in the
 * following format:
 * 
 * <pre>
 * Item-Range: items={offset}-{last-index}[;{sortField}[:{sortDir}]]
 * </pre>
 * <ul>
 * <li><code>offset</code><br/>
 * The offset in the result.
 * <li><code>last-index</code><br/>
 * The last index of the result. The <code>last-index</code> is used to
 * calculate the size of one page:
 * <code>pageSize = lastIndex - offsetValue + 1</code>
 * <li><code>sortField</code><br/>
 * The name of the field used for srting the result (optional)
 * <li><code>sortDir</code><br/>
 * The sort direction. Must match (case insensitiv) one of the constants in
 * {@link SortDir}
 * </ul>
 * <p>
 * Examples:
 * <ul>
 * <li>Item-Range: items=0-24
 * <li>Item-Range: items=25-49;surname
 * <li>Item-Range: items=100-124;createdAt:dEsC
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2012-03-02 11:06:21 +0100 (Fr, 02 Mrz 2012) $ $Revision:
 *          85318 $
 */
public abstract class PagingHeaderResource extends AbstractPagingResource<HttpHeaders>
{
    /**
     * The name of the custom header carrying the item range data.
     */
    public static final String ITEM_RANGE_HEADER = "Item-Range";
    private static final String REGEXP = "^items=([0-9]+)-([0-9]+)";


    @Override
    protected PageInfo getPageInfo(HttpHeaders input) throws PageInfoParseException
    {
        PageInfo result = null;
        if (input != null)
        {
            List<String> requestHeader = input.getRequestHeader(ITEM_RANGE_HEADER);
            if (requestHeader != null && !requestHeader.isEmpty())
            {
                String headerValue = requestHeader.get(0);
                Pattern p = Pattern.compile(REGEXP);
                Matcher m = p.matcher(headerValue);

                String offset = null;
                String lastIndex = null;
                if (m.matches() && m.groupCount() > 1)
                {
                    offset = m.group(1);
                    lastIndex = m.group(2);

                    int offsetValue = convertInt(offset, "Paging info \"%s\" contains the invalid offset: \"%s\"",
                            headerValue, offset);
                    int lastIndexValue = convertInt(lastIndex,
                            "Paging info \"%s\" contains the invalid last index: \"%s\"", headerValue, lastIndex);
                    int pageSize = lastIndexValue - offsetValue + 1;
                    result = new PageInfo(offsetValue, pageSize);
                }
                else
                {
                    String error = String.format("Paging info has the wrong format. Expected: %s, given: \"%s\"",
                            REGEXP, headerValue);
                    throw new PageInfoParseException(error);
                }
            }
        }
        return result;
    }
}
