package name.pehl.tire.client;

import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.gin.TireGinjector;
import name.pehl.tire.client.settings.CurrentSettings;
import name.pehl.tire.client.settings.GetSettingsAction;
import name.pehl.tire.client.settings.GetSettingsResult;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 95 $
 */
public class Tire implements EntryPoint
{
    public final TireGinjector ginjector = GWT.create(TireGinjector.class);


    @Override
    public void onModuleLoad()
    {
        DelayedBindRegistry.bind(ginjector);
        ginjector.getDispathcer().execute(new GetSettingsAction(),
                new TireCallback<GetSettingsResult>(ginjector.getEventBus())
                {
                    @Override
                    public void onSuccess(GetSettingsResult result)
                    {
                        CurrentSettings.set(result.getSettings());
                    }
                });
        ginjector.getPlaceManager().revealCurrentPlace();
    }
}
