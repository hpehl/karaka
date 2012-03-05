package name.pehl.tire.server.model;

import name.pehl.tire.shared.model.Status;

import com.googlecode.objectify.condition.ValueIf;

/**
 * Condition to setup a partial index on {@link Activity#getStatus()}. This way
 * only active (status != {@link Status#STOPPED} ) activities are indexed.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class IfStopped extends ValueIf<Status>
{
    /**
     * Returns <code>true</code> if status == {@link Status#STOPPED},
     * <code>false</code> otherwise.
     * 
     * @param status
     * @return <code>true</code> if status == {@link Status#STOPPED},
     *         <code>false</code> otherwise.
     * @see com.googlecode.objectify.condition.ValueIf#matches(java.lang.Object)
     */
    @Override
    public boolean matches(Status status)
    {
        return status == Status.STOPPED;
    }
}
