package name.pehl.tire.client;

import name.pehl.tire.client.gin.TireGinjector;

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
        ginjector.getPlaceManager().revealCurrentPlace();
        ginjector.getStartupManager().startup();
    }
}
