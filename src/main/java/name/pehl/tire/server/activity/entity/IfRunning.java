package name.pehl.tire.server.activity.entity;

import name.pehl.tire.shared.model.Status;

import com.googlecode.objectify.condition.ValueIf;

import static name.pehl.tire.shared.model.Status.RUNNING;

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
     * @see com.googlecode.objectify.condition.ValueIf#matches(java.lang.Object)
     */
    @Override
    public boolean matches(Status status)
    {
        return status == RUNNING;
    }
}
