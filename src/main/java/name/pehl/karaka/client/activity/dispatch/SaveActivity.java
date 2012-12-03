package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

/**
 * Used to save new and update existing placeRequestFor.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class SaveActivity
{
    @In(1) Activity activity;
    @Out(1) Activity saved;
}
