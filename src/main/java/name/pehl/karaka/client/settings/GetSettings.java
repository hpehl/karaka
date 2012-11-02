package name.pehl.karaka.client.settings;

import name.pehl.karaka.shared.model.Settings;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetSettings
{
    @Out(1) Settings settings;
}
