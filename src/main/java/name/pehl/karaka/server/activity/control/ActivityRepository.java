package name.pehl.karaka.server.activity.control;

import com.google.appengine.api.datastore.EntityNotFoundException;
import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.repository.Repository;
import name.pehl.karaka.shared.model.Status;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static name.pehl.karaka.shared.model.Status.STOPPED;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:00:49 +0100 (Mi, 01. Dez 2010) $ $Revision: 121
 *          $
 */
public class ActivityRepository extends Repository<Activity>
{
    @Inject
    public ActivityRepository(ActivityIndexSearch indexSearch)
    {
        super(Activity.class, indexSearch);
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
        return query().filter("start.year =", year).filter("start.month =", month).filter("start.day =", day)
                .count() > 0;
    }

    /**
     * Finds the activity with {@link Status#RUNNING}. If no activity is
     * {@link Status#RUNNING} <code>null</code> is returned.
     *
     * @return the activity with {@link Status#RUNNING}, <code>null</code>
     *         otherwise.
     *
     * @throws EntityNotFoundException
     * @throws IllegalStateException   if more thaan one activity is {@link Status#RUNNING}.
     */
    public Activity findRunningActivity()
    {
        Activity activity = null;
        List<Activity> activities = query().filter("status =", Status.RUNNING).list();
        if (!activities.isEmpty())
        {
            if (activities.size() > 1)
            {
                throw new IllegalStateException("Found more than 1 activity with status RUNNING!");
            }
            activity = activities.get(0);
        }
        return activity;
    }

    public Activity findLatestActivity()
    {
        return query().order("start.date").first().get();
    }

    /**
     * Starts or resumes the specified activity. If the placeRequestFor init day is today, the activity is resumed. If not
     * the activity is cloned and started as a new activity. If there's another activity running, this activity is
     * stopped first. The returned set contains all modified placeRequestFor (two if there was another activity
     * running, one otherwise).
     *
     * @param activity
     *
     * @return
     */
    public Iterable<Activity> start(final Activity activity)
    {
        if (activity != null)
        {
            Set<Activity> saveMe = new HashSet<Activity>();
            Activity runningActivity = findRunningActivity();
            if (runningActivity != null && !runningActivity.equals(activity))
            {
                runningActivity.stop();
                saveMe.add(runningActivity);
            }
            // same day --> resume, init otherwise
            if (activity.isToday())
            {
                activity.resume();
                saveMe.add(activity);
            }
            else
            {
                Activity copy = activity.copy();
                copy.start();
                saveMe.add(copy);
            }
            return saveAll(saveMe);
        }
        return Collections.emptySet();
    }

    /**
     * Ticks the specified activity. If the activity is not running it is started. If there's another activity running,
     * this activity is stopped first. The returned set contains all modified placeRequestFor (two if there was another
     * activity running, one otherwise).
     *
     * @param activity
     *
     * @return
     */
    public Iterable<Activity> tick(final Activity activity)
    {
        if (activity != null)
        {
            Set<Activity> saveMe = new HashSet<Activity>();
            Activity runningActivity = findRunningActivity();
            if (runningActivity != null && !runningActivity.equals(activity))
            {
                runningActivity.stop();
                saveMe.add(runningActivity);
            }
            if (activity.getStatus() == STOPPED)
            {
                activity.start();
            }
            activity.tick();
            saveMe.add(activity);
            return saveAll(saveMe);
        }
        return Collections.emptySet();
    }
}
