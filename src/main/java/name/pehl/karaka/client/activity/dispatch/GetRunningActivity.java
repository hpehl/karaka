package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetRunningActivity
{
    @Out(1) Activity activity;
}
