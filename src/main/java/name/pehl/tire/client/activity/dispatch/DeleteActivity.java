package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

/**
 * Used to save new and update existing activities.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class DeleteActivity
{
    @In(1) Activity activity;
}
