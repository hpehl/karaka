package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.shared.model.Minutes;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetMinutes
{
    @Out(1) Minutes minutes;
}
