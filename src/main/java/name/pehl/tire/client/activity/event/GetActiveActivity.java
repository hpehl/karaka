package name.pehl.tire.client.activity.event;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetActiveActivity
{
    @Out(1) Activity activity;
}
