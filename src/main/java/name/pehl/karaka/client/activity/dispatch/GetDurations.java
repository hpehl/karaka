package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Durations;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetDurations
{
    @Out(1) Durations minutes;
}
