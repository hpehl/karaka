package name.pehl.tire.server.xml;

import java.util.HashMap;
import java.util.Map;


/**
 * @author $Author:$
 * @version $Revision:$
 */
public class Context
{
    private Map<String, Object> data;


    public Context()
    {
        data = new HashMap<String, Object>();
    }


    public Object get(String key)
    {
        return data.get(key);
    }


    public void set(String key, Object value)
    {
        data.put(key, value);
    }


    public Map<String, Object> asMap()
    {
        return data;
    }
}
