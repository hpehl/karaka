package name.pehl.karaka.server.activity.entity;

import name.pehl.karaka.shared.model.Status;

import com.googlecode.objectify.condition.ValueIf;

import static name.pehl.karaka.shared.model.Status.RUNNING;

/**
 * Condition to setup a partial index on {@link Activity#getStatus()}. This way
 * only the running activity is indexed.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class IfRunning extends ValueIf<Status>
{
    /**
     * Returns <code>true</code> if status == {@link Status#RUNNING},
     * <code>false</code> otherwise.
     * 
     * @param status
     * @return <code>true</code> if status == {@link Status#RUNNING},
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean matchesValue(Status status)
    {
        return status == RUNNING;
    }
}
