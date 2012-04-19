package name.pehl.tire.server.activity.control;

import java.util.EnumSet;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.normalizer.Normalizer;
import name.pehl.tire.server.normalizer.TireNormalizer;
import name.pehl.tire.server.repository.NamedEntityRepository;
import name.pehl.tire.shared.model.Status;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:00:49 +0100 (Mi, 01. Dez 2010) $ $Revision: 121
 *          $
 */
@ApplicationScoped
public class ActivityRepository extends NamedEntityRepository<Activity>
{
    @Inject
    public ActivityRepository(@TireNormalizer Normalizer normalizer)
    {
        super(Activity.class, normalizer);
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


    /**
     * Finds the activity with {@link Status#RUNNING} or {@link Status#PAUSE}.
     * If no activity is either {@link Status#RUNNING} or {@link Status#PAUSE}
     * <code>null</code> is returned.
     * 
     * @return the activity with {@link Status#RUNNING} or {@link Status#PAUSE},
     *         <code>null</code> otherwise.
     * @throws EntityNotFoundException
     * @throws IllegalStateException
     *             if more thaan one activity is {@link Status#RUNNING} or
     *             {@link Status#PAUSE}.
     */
    public Activity findActiveActivity() throws EntityNotFoundException
    {
        Activity activity = null;
        List<Key<Activity>> keys = query().filter("status IN", EnumSet.of(Status.RUNNING, Status.PAUSE)).listKeys();
        if (!keys.isEmpty())
        {
            if (keys.size() > 1)
            {
                throw new IllegalStateException("Found more than 1 activity with status RUNNING OR PAUSE!");
            }
            activity = get(keys.get(0));
        }
        return activity;
    }
}
