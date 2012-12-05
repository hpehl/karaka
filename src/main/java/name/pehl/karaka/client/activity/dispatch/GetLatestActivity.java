package name.pehl.karaka.client.activity.dispatch;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;
import name.pehl.karaka.shared.model.Activity;

@GenDispatch
public class GetLatestActivity
{
    @Out(1) Activity activity;
}
