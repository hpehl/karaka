package name.pehl.tire.model;

import com.googlecode.objectify.Key;

/**
 * @author $Author$
 * @version $Date$ $Revision: 138
 *          $
 */
public class IndexEntry extends BaseEntity
{
    private final Key<?> key;
    private final String data;


    IndexEntry()
    {
        this(null, null);
    }


    public IndexEntry(Key<?> key, String index)
    {
        super();
        this.key = key;
        this.data = index;
    }


    @SuppressWarnings("unchecked")
    public <T> Key<T> getKey()
    {
        return (Key<T>) key;
    }


    public String getData()
    {
        return data;
    }
}
