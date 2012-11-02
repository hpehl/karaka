package name.pehl.karaka.server.activity.control;

import java.util.List;

import javax.inject.Inject;

import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.repository.NamedEntityRepository;
import name.pehl.karaka.shared.model.Status;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:00:49 +0100 (Mi, 01. Dez 2010) $ $Revision: 121
 *          $
 */
public class ActivityRepository extends NamedEntityRepository<Activity>
{
    @Inject
    public ActivityRepository(ActivityIndexSearch indexSearch)
    {
        super(Activity.class, indexSearch);
    }


    public List<Activity> findByYear(int year)
    {
        return query().filter("start.year =", year).list();
    }


    public boolean hasActivitiesByYear(int year)
    {
        return query().filter("start.year =", year).count() > 0;
    }


    public List<Activity> findByYearMonth(int year, int month)
    {
        return query().filter("start.year =", year).filter("start.month =", month).list();
    }


    public boolean hasActivitiesByYearMonth(int year, int month)
    {
        return query().filter("start.year =", year).filter("start.month =", month).count() > 0;
    }


    public List<Activity> findByYearWeek(int year, int week)
    {
        return query().filter("start.year =", year).filter("start.week =", week).list();
    }


    public boolean hasActivitiesByYearWeek(int year, int week)
    {
        return query().filter("start.year =", year).filter("start.week =", week).count() > 0;
    }


    public List<Activity> findByYearMonthDay(int year, int month, int day)
    {
        return query().filter("start.year =", year).filter("start.month =", month).filter("start.day =", day).list();
    }


    public boolean hasActivitiesByYearMonthDay(int year, int month, int day)
    {
        return query().filter("start.year =", year).filter("start.month =", month).filter("start.day =", day).count() > 0;
    }


    /**
     * Finds the activity with {@link Status#RUNNING}. If no activity is
     * {@link Status#RUNNING} <code>null</code> is returned.
     * 
     * @return the activity with {@link Status#RUNNING}, <code>null</code>
     *         otherwise.
     * @throws EntityNotFoundException
     * @throws IllegalStateException
     *             if more thaan one activity is {@link Status#RUNNING}.
     */
    public Activity findRunningActivity()
    {
        Activity activity = null;
        List<Key<Activity>> keys = query().filter("status =", Status.RUNNING).listKeys();
        if (!keys.isEmpty())
        {
            if (keys.size() > 1)
            {
                throw new IllegalStateException("Found more than 1 activity with status RUNNING!");
            }
            activity = get(keys.get(0));
        }
        return activity;
    }
}
