package name.pehl.tire.dao;

import java.util.List;

import name.pehl.tire.model.Activity;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import com.googlecode.objectify.Query;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ActivityDao extends HasUserDao<Activity>
{
    @Inject
    public ActivityDao(User user)
    {
        super(Activity.class, user);
    }


    public List<Activity> findByYearMonth(int year, int month)
    {
        Query<Activity> q = ofy().query(clazz);
        return addUserFilter(q.filter("start.year", year).filter("start.month", month)).list();
    }


    public List<Activity> findByYearWeek(int year, int week)
    {
        Query<Activity> q = ofy().query(clazz);
        return addUserFilter(q.filter("start.year", year).filter("start.week", week)).list();
    }


    public List<Activity> findByYearMonthDay(int year, int month, int day)
    {
        Query<Activity> q = ofy().query(clazz);
        return addUserFilter(q.filter("start.year", year).filter("start.month", month).filter("start.day", day)).list();
    }
}
