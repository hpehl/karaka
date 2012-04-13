package name.pehl.tire.server.search.entity;

import name.pehl.tire.server.entity.BaseEntity;

import com.googlecode.objectify.Key;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:06:40 +0200 (Mi, 31. Aug 2011) $ $Revision: 138
 *          $
 */
public class IndexEntry extends BaseEntity
{
    private static final long serialVersionUID = 5319841455603288912L;

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
