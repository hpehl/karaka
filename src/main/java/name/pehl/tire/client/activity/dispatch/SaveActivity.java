package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenDispatch
public class SaveActivity
{
    @In(1) Activity newOrModifiedActivity;
    @Out(1) Activity storedActivity;
}
