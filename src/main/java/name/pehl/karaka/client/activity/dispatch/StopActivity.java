package name.pehl.karaka.client.activity.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import name.pehl.karaka.shared.model.Activity;

/**
 * Used to save new and update existing placeRequestFor.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class StopActivity
{
    @In(1) Activity activity;
    @Out(1) Activity stopped;
}
