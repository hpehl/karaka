package name.pehl.taoki.paging;

import java.io.Serializable;

/**
 * Immutable value object for paging over large data. The class holds the
 * following information:
 * <ul>
 * <li>offset
 * <li>page size
 * </ul>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-01-28 09:37:21 +0100 (Mi, 28 Jan 2009) $ $Revision:
 *          61016 $
 */
public class PageInfo implements Serializable
{
    // -------------------------------------------------------------- constants

    public static final int MIN_OFFSET = 0;
    public static final int MAX_OFFSET = 0xffffff / 2;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int MAX_PAGE_SIZE = 0xffffff;

    private static final long serialVersionUID = 1L;

    // -------------------------------------------------------- private members

    private final int offset;
    private final int pageSize;


    // ----------------------------------------------------------- constructors

    /**
     * Construct a new instance using the specified offset and page size.
     * 
     * @param offset
     *            The offset. If &lt; {@value #MIN_OFFSET}, offset is set to
     *            {@value #MIN_OFFSET}. If &gt; {@value #MAX_OFFSET}, offset is
     *            set to {@value #MAX_OFFSET}.
     * @param pageSize
     *            The page size. If &lt; {@value #MIN_PAGE_SIZE}, page size is
     *            set to {@value #MIN_PAGE_SIZE}. If &gt;
     *            {@value #MAX_PAGE_SIZE}, page size is set to
     *            {@value #MAX_PAGE_SIZE}.
     */
    public PageInfo(int offset, int pageSize)
    {
        super();
        if (offset < MIN_OFFSET)
        {
            this.offset = MIN_OFFSET;
        }
        else if (offset > MAX_OFFSET)
        {
            this.offset = MAX_OFFSET;
        }
        else
        {
            this.offset = offset;
        }
        if (pageSize < MIN_PAGE_SIZE)
        {
            this.pageSize = MIN_PAGE_SIZE;
        }
        else if (pageSize > MAX_PAGE_SIZE)
        {
            this.pageSize = MAX_PAGE_SIZE;
        }
        else
        {
            this.pageSize = pageSize;
        }
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on {@link #getOffset()}, {@link #getPageSize()} and
     * {@link #getSortInfo()}.
     * 
     * @return the hash code base on {@link #getOffset()},
     *         {@link #getPageSize()} and {@link #getSortInfo()}.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + offset;
        result = prime * result + pageSize;
        return result;
    }


    /**
     * Based on {@link #getOffset()}, {@link #getPageSize()} and
     * {@link #getSortInfo()}.
     * 
     * @param o
     *            the other {@link PageInfo} instance
     * @return <code>true</code> if this instance equals {@code o},
     *         <code>false</code> otherwise.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null)
        {
            return false;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
        PageInfo other = (PageInfo) o;
        if (offset != other.offset)
        {
            return false;
        }
        if (pageSize != other.pageSize)
        {
            return false;
        }
        return true;
    }


    /**
     * Returns a string representation in the form
     * <code>PageInfo[{@link #getOffset()}/{@link #getPageSize()}]</code>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder("PageInfo [").append(offset).append("/").append(pageSize).append("/").append("]")
                .toString();
    }


    // ------------------------------------------------------------- navigation

    /**
     * Creates a new {@link PageInfo} instance with the offset for the previous
     * page.
     * 
     * @return the previous {@link PageInfo}
     */
    public PageInfo previous()
    {
        return new PageInfo(getOffset() - pageSize, getPageSize());
    }


    /**
     * Returns the next {@link PageInfo} instance.
     * 
     * @return
     */
    public PageInfo next()
    {
        return new PageInfo(getOffset() + pageSize, getPageSize());
    }


    // ------------------------------------------------------------- properties

    /**
     * @return the offset.
     */
    public int getOffset()
    {
        return offset;
    }


    /**
     * @return the page size.
     */
    public int getPageSize()
    {
        return pageSize;
    }
}
