package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.shared.model.Years;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetYears
{
    @Out(1) Years years;
}
