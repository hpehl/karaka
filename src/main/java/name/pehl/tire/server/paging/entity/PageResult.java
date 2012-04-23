package name.pehl.tire.server.paging.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Contains the results for one page of a larger dataset based on a
 * {@link PageInfo} instance.
 * <p>
 * The internal state and behaviour of this class depends on the constructor
 * called:
 * <ol>
 * <li>{@link #PageResult(PageInfo, List)}<br/>
 * In this mode the complete data is known. The methods {@link #first()},
 * {@link #previous()}, {@link #getPage(int)}, {@link #next()} and
 * {@link #last()} can be used to navigate over the data.
 * <li>{@link #PageResult(PageInfo, List, int)}<br/>
 * In this mode only the data of the specified page is known. The methods
 * {@link #first()}, {@link #previous()}, {@link #getPage(int)}, {@link #next()}
 * and {@link #last()} all return the current instance.
 * </ol>
 * 
 * @param <T>
 *            The type of the result
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 10:25:44 +0100 (Fr, 17. Dez 2010) $
 */
public class PageResult<T> implements Iterable<T>
{
    // -------------------------------------------------------------- constants

    public static final int MIN_TOTAL = 0;
    public static final int MAX_TOTAL = 0xffffff;

    // -------------------------------------------------------- private members

    private final PageInfo pageInfo;
    private final List<T> all;
    private final List<T> page;
    private final int pageIndex;
    private final int pages;
    private final int total;


    // ----------------------------------------------------------- constructors

    /**
     * Creates a new instance which contains one page from {@code all} according
     * to {@code pageInfo}. The specified list is expected to contain all data
     * and is used when navigating over it by calling {@link #first()},
     * {@link #previous()}, {@link #getPage(int)}, {@link #next()} and
     * {@link #last()}. The {@linkplain #getTotal() total number of records} is
     * computed from <code>all.size()</code>.
     * 
     * @param pageInfo
     *            The pageInfo instance which is used to create the sublist.
     *            Must not be <code>null</code>!
     * @param all
     *            The complete list. Might be <code>null</code>. In this case
     *            {@link #getTotal()} will return 0, {@link #getPage()} will
     *            return an empty list, {@link #isNavigable()} will return
     *            <code>false</code> and {@link #first()}, {@link #previous()},
     *            {@link #getPage(int)}, {@link #next()} and {@link #last()}
     *            will all return <code>null</code>.
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    public PageResult(final PageInfo pageInfo, final List<T> all)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = all;
        if (all != null)
        {
            this.total = all.size();
            if (total % pageInfo.getPageSize() == 0)
            {
                this.pages = max(1, total / pageInfo.getPageSize());
            }
            else
            {
                this.pages = 1 + total / pageInfo.getPageSize();
            }
            this.pageIndex = pageInfo.getOffset() / pageInfo.getPageSize();
            this.page = sublist(pageInfo, all);
        }
        else
        {
            this.total = 0;
            this.pages = 1;
            this.pageIndex = 0;
            this.page = Collections.emptyList();
        }
    }


    /**
     * Creates a new instance with exactly the specified data. The complete data
     * is unknown to the created instance and thus the methods {@link #first()},
     * {@link #previous()}, {@link #getPage(int)}, {@link #next()} and
     * {@link #last()} will all return the current instance. The
     * {@linkplain #getTotal() total number of records} must be specified by the
     * parameter {@code total}. It must be between {@value #MIN_TOTAL} and
     * {@value #MAX_TOTAL} and &gt; <code>page.size()</code>.
     * 
     * @param pageInfo
     *            The pageInfo instance. Only {@link PageInfo#getPageSize()} is
     *            used to shorten the list in case
     *            <code>page.size &gt; pageInfo.getLimit()</code>. Must not be
     *            <code>null</code>
     * @param page
     *            The data for this page.
     * @param total
     *            The total number of records as it cannot be calculated in this
     *            case.
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    public PageResult(final PageInfo pageInfo, final List<T> page, final int total)
    {
        this.pageInfo = checkPageInfo(pageInfo);
        this.all = null;
        int validTotal = 0;
        if (total < MIN_TOTAL)
        {
            validTotal = MIN_TOTAL;
        }
        else if (total > MAX_TOTAL)
        {
            validTotal = MAX_TOTAL;
        }
        else
        {
            validTotal = total;
        }
        if (page != null)
        {
            this.page = new ArrayList<T>(page.subList(0, min(page.size(), pageInfo.getPageSize())));
            this.total = max(validTotal, page.size());
        }
        else
        {
            this.page = Collections.emptyList();
            this.total = validTotal;
        }
        this.pages = 1;
        this.pageIndex = 0;
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on {@link #pageInfo}, {@link #getPage()} and {@link #getTotal()}.
     * 
     * @return the hash code based on {@link #pageInfo}, {@link #getPage()} and
     *         {@link #getTotal()}.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pageInfo == null) ? 0 : pageInfo.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + total;
        return result;
    }


    /**
     * Based on {@link #pageInfo}, {@link #getPage()} and {@link #getTotal()}.
     * 
     * @param o
     *            the other {@link PageResult} instance
     * @return <code>true</code> if this instance equals {@code o},
     *         <code>false</code> otherwise.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
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
        PageResult<T> other = (PageResult<T>) o;
        if (page == null)
        {
            if (other.page != null)
            {
                return false;
            }
        }
        else if (!page.equals(other.page))
        {
            return false;
        }
        if (pageInfo == null)
        {
            if (other.pageInfo != null)
            {
                return false;
            }
        }
        else if (!pageInfo.equals(other.pageInfo))
        {
            return false;
        }
        if (total != other.total)
        {
            return false;
        }
        return true;
    }


    /**
     * Returns a string representation in the form
     * <code>PageResult[{@link PageInfo#getOffset()}/{@link #size()}/{@link #getTotal()}/{@link #isNavigable()}]</code>
     * 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder("PageResult [").append(pageInfo.getOffset()).append("/").append(size()).append("/")
                .append(getTotal()).append("/").append(isNavigable()).append("]").toString();
    }


    // --------------------------------------------------------- helper methods

    /**
     * Throws an {@link IllegalArgumentException} in case {@code pageInfo} is
     * <code>null</code>.
     * 
     * @param pageInfo
     *            The {@link PageInfo} instance to check.
     * @return
     * @throws IllegalArgumentException
     *             if {@code pageInfo} is <code>null</code>.
     */
    private PageInfo checkPageInfo(final PageInfo pageInfo) throws IllegalArgumentException
    {
        if (pageInfo == null)
        {
            throw new IllegalArgumentException("pageInfo must not be null");
        }
        return pageInfo;
    }


    /**
     * Creates a sublist of the specified list according to the given
     * {@link PageInfo} instance.
     * 
     * @param pageInfo
     *            The pageInfo instance which is used to create the sublist.
     * @param all
     *            The complete list.
     * @return A sublist which contains only the data according to
     *         {@code pageInfo}.
     */
    private List<T> sublist(final PageInfo pageInfo, final List<T> all)
    {
        if (all.isEmpty())
        {
            return Collections.emptyList();
        }
        if (pageInfo.getOffset() == 0 && all.size() < pageInfo.getPageSize())
        {
            return new ArrayList<T>(all);
        }

        List<T> page = new ArrayList<T>();
        int from = pageInfo.getOffset();
        int to = from + pageInfo.getPageSize();
        int size = all.size();
        if (from >= size)
        {
            from = size - 1;
        }
        if (to > size)
        {
            to = size;
        }
        page.addAll(all.subList(from, to));
        return page;
    }


    // ------------------------------------------------------------- navigation

    /**
     * Returns the {@link PageResult} instance representing the first page if
     * this instance is {@linkplain #isNavigable() navigable}, the current
     * instance otherwise.
     * 
     * @return the {@link PageResult} instance representing the first page if
     *         this instance is {@linkplain #isNavigable() navigable}, the
     *         current instance otherwise.
     */
    public PageResult<T> first()
    {
        if (isNavigable())
        {
            PageResult<T> first = new PageResult<T>(new PageInfo(0, pageInfo.getPageSize()), all);
            return first;
        }
        return this;
    }


    /**
     * Returns <code>true</code> if this instance is {@linkplain #isNavigable()
     * navigable} and has a previous page, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if this instance is {@linkplain #isNavigable()
     *         navigable} and has a previous page, <code>false</code> otherwise.
     */
    public boolean hasPrevious()
    {
        if (isNavigable())
        {
            int previousOffset = pageInfo.getOffset() - pageInfo.getPageSize();
            return previousOffset >= 0;
        }
        return false;
    }


    /**
     * Returns the {@link PageResult} instance representing the previous page if
     * this instance is {@linkplain #isNavigable() navigable}, the current
     * instance otherwise.
     * 
     * @return the {@link PageResult} instance representing the previous page if
     *         this instance is {@linkplain #isNavigable() navigable}, the
     *         current instance otherwise.
     */
    public PageResult<T> previous()
    {
        if (isNavigable())
        {
            PageResult<T> previous = new PageResult<T>(pageInfo.previous(), all);
            return previous;
        }
        return this;
    }


    /**
     * Returns <code>true</code> if this instance is {@linkplain #isNavigable()
     * navigable} and has a next page, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if this instance is {@linkplain #isNavigable()
     *         navigable} and has a next page, <code>false</code> otherwise.
     */
    public boolean hasNext()
    {
        if (isNavigable())
        {
            int nextOffset = pageInfo.getOffset() + pageInfo.getPageSize();
            return nextOffset < getTotal();
        }
        return false;
    }


    /**
     * Returns the {@link PageResult} instance representing the next page if
     * this instance is {@linkplain #isNavigable() navigable}, the current
     * instance otherwise.
     * 
     * @return the {@link PageResult} instance representing the next page if
     *         this instance is {@linkplain #isNavigable() navigable}, the
     *         current instance otherwise.
     */
    public PageResult<T> next()
    {
        if (isNavigable())
        {
            PageResult<T> next = new PageResult<T>(pageInfo.next(), all);
            return next;
        }
        return this;
    }


    /**
     * Returns the {@link PageResult} instance representing the last page if
     * this instance is {@linkplain #isNavigable() navigable}, the current
     * instance otherwise.
     * 
     * @return the {@link PageResult} instance representing the last page if
     *         this instance is {@linkplain #isNavigable() navigable}, the
     *         current instance otherwise.
     */
    public PageResult<T> last()
    {
        if (isNavigable())
        {
            int lastOffset = 0;
            if (getTotal() > pageInfo.getPageSize())
            {
                int remainder = getTotal() % pageInfo.getPageSize();
                lastOffset = remainder != 0 ? getTotal() - remainder : getTotal() - pageInfo.getPageSize();
            }
            PageResult<T> last = new PageResult<T>(new PageInfo(lastOffset, pageInfo.getPageSize()), all);
            return last;
        }
        return this;
    }


    // ------------------------------------------------------------- properties

    /**
     * Returns the data which reflects the current page of this
     * {@link PageResult} instance.
     * 
     * @return the current page data.
     */
    public List<T> getPage()
    {
        return page;
    }


    /**
     * Returns the {@link PageResult} instance representing the page at the
     * specified index if this instance is {@linkplain #isNavigable() navigable}
     * , the current instance otherwise.
     * 
     * @param index
     *            the page index
     * @return the {@link PageResult} instance representing the page at the
     *         specified index if this instance is {@linkplain #isNavigable()
     *         navigable}, the current instance otherwise.
     * @throws IndexOutOfBoundsException
     *             if {@code index} &lt; 0 or &gt; {@link #getPages()} - 1
     */
    public PageResult<T> getPage(int index)
    {
        if (index < 0 || index > pages - 1)
        {
            throw new IndexOutOfBoundsException(String.format("Page index %d out of bounds. Valid range: [0, %d]",
                    index, pages - 1));
        }
        if (isNavigable())
        {
            int offset = index * pageInfo.getPageSize();
            PageResult<T> pageAtIndex = new PageResult<T>(new PageInfo(offset, pageInfo.getPageSize()), all);
            return pageAtIndex;
        }
        return this;
    }


    /**
     * Returns the index of the current page if this instance is
     * {@linkplain #isNavigable() navigable}, 0 otherwise.
     * 
     * @return the index of the current page if this instance is
     *         {@linkplain #isNavigable() navigable}, 0 otherwise.
     */
    public int getPageIndex()
    {
        return pageIndex;
    }


    /**
     * If this instance contains no data, the method returns 0. If this instance
     * is {@linkplain #isNavigable() navigable}, the method returns the number
     * of pages. Otherwise the method returns 1.
     * 
     * @return the number of pages.
     */
    public int getPages()
    {
        return pages;
    }


    /**
     * The total number of records.
     * 
     * @return the total number of records.
     */
    public int getTotal()
    {
        return total;
    }


    /**
     * Returns the {@link PageInfo} instance.
     * 
     * @return the {@link PageInfo} instance.
     */
    public PageInfo getPageInfo()
    {
        return pageInfo;
    }


    /**
     * Returns <code>true</code> if this instance was created using
     * {@link #PageResult(PageInfo, List)} and the {@code all} parameter was not
     * <code>null</code>. In this case the methods {@link #first()},
     * {@link #previous()}, {@link #getPage(int)}, {@link #next()} and
     * {@link #last()} will return a relevant {@link PageResult} instance.
     * <p>
     * If this instance was created using
     * {@link #PageResult(PageInfo, List, int)} the method returns
     * <code>false</code>. In this case the methods {@link #first()},
     * {@link #previous()}, {@link #getPage(int)}, {@link #next()} and
     * {@link #last()} will all return the current instance.
     * 
     * @return <code>true</code> if this {@link PageResult} is navigable,
     *         <code>false</code> otherwise.
     */
    public boolean isNavigable()
    {
        return all != null;
    }


    // -------------------------------------------------- page delegate methods

    /**
     * Delegates to <code>{@link #getPage()}.iterator()</code>.
     * 
     * @return
     * @see List#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return page.iterator();
    }


    /**
     * Delegates to <code>{@link #getPage()}.size()</code>.
     * 
     * @return
     * @see List#size()
     */
    public int size()
    {
        return page.size();
    }


    /**
     * Delegates to <code>{@link #getPage()}.isEmpty()</code>.
     * 
     * @return
     * @see List#isEmpty()
     */
    public boolean isEmpty()
    {
        return page.isEmpty();
    }


    /**
     * Delegates to <code>{@link #getPage()}.contains()</code>.
     * 
     * @param o
     * @return
     * @see List#contains(Object)
     */
    public boolean contains(Object o)
    {
        return page.contains(o);
    }


    /**
     * Delegates to <code>{@link #getPage()}.toArray()</code>.
     * 
     * @return
     * @see List#toArray()
     */
    public Object[] toArray()
    {
        return page.toArray();
    }


    /**
     * Delegates to <code>{@link #getPage()}.toArray(T[])</code>.
     * 
     * @param a
     * @return
     * @see List#toArray(Object[])
     */
    public T[] toArray(T[] a)
    {
        return page.toArray(a);
    }


    /**
     * Delegates to <code>{@link #getPage()}.containsAll(Collection<?>)</code>.
     * 
     * @param c
     * @return
     * @see List#containsAll(Collection)
     */
    public boolean containsAll(Collection<?> c)
    {
        return page.containsAll(c);
    }


    /**
     * Delegates to <code>{@link #getPage()}.get(int)</code>.
     * 
     * @param index
     * @return
     * @see List#get(int)
     */
    public T get(int index)
    {
        return page.get(index);
    }


    /**
     * Delegates to <code>{@link #getPage()}.indexOf(Object)</code>.
     * 
     * @param o
     * @return
     * @see List#indexOf(Object)
     */
    public int indexOf(Object o)
    {
        return page.indexOf(o);
    }


    /**
     * Delegates to <code>{@link #getPage()}.lastIndexOf(Object)</code>.
     * 
     * @param o
     * @return
     * @see List#lastIndexOf(Object)
     */
    public int lastIndexOf(Object o)
    {
        return page.lastIndexOf(o);
    }


    /**
     * Delegates to <code>{@link #getPage()}.listIterator()</code>.
     * 
     * @return
     * @see List#listIterator()
     */
    public ListIterator<T> listIterator()
    {
        return page.listIterator();
    }


    /**
     * Delegates to <code>{@link #getPage()}.listIterator(int)</code>.
     * 
     * @return
     * @see List#listIterator(int)
     */
    public ListIterator<T> listIterator(int index)
    {
        return page.listIterator(index);
    }


    /**
     * Delegates to <code>{@link #getPage()}.subList(int, int)</code>.
     * 
     * @param fromIndex
     * @param toIndex
     * @return
     * @see List#subList(int, int)
     */
    public List<T> subList(int fromIndex, int toIndex)
    {
        return page.subList(fromIndex, toIndex);
    }
}
