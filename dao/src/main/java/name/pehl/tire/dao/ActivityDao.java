package name.pehl.tire.dao;

import java.util.List;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.Activity;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ActivityDao extends NamedEntityDao<Activity>
{
    @Inject
    public ActivityDao(User user, Normalizer normalizer)
    {
        super(Activity.class, user, normalizer);
    }


    public List<Activity> findByYearMonth(int year, int month)
    {
        return query().filter("start.year", year).filter("start.month", month).list();
    }


    public List<Activity> findByYearWeek(int year, int week)
    {
        return query().filter("start.year", year).filter("start.week", week).list();
    }


    public List<Activity> findByYearMonthDay(int year, int month, int day)
    {
        return query().filter("start.year", year).filter("start.month", month).filter("start.day", day).list();
    }
}
