package name.pehl.karaka.client.settings;

import name.pehl.karaka.shared.model.Settings;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class SettingsChanged
{
    @Order(1) Settings settings;
}
