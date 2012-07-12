package name.pehl.tire.client.settings;

import name.pehl.tire.shared.model.Settings;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

@GenEvent
public class SettingsChanged
{
    @Order(1) Settings settings;
}
