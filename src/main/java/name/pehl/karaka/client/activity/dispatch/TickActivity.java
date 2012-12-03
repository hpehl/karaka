package name.pehl.karaka.client.activity.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import name.pehl.karaka.shared.model.Activity;

import java.util.Set;

/**
 * Used to save new and update existing placeRequestFor.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class TickActivity
{
    @In(1) Activity activity;
    @Out(1) Set<Activity> modified;
}
