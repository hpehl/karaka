package name.pehl.tire.server.project;

import name.pehl.tire.server.model.Activity;
import name.pehl.tire.server.persistence.ObjectifyDao;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivityDao extends ObjectifyDao<Activity>
{
    static
    {
        ObjectifyService.register(Activity.class);
    }


    public ActivityDao()
    {
        super(Activity.class);
    }
}
