package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

/**
 * Used to save new and update existing placeRequestFor.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class DeleteActivity
{
    @In(1) Activity activity;
}
