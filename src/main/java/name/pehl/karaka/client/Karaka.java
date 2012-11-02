package name.pehl.karaka.client;

import name.pehl.karaka.client.gin.KarakaGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 95 $
 */
public class Karaka implements EntryPoint
{
    public final KarakaGinjector ginjector = GWT.create(KarakaGinjector.class);


    @Override
    public void onModuleLoad()
    {
        DelayedBindRegistry.bind(ginjector);
        ginjector.getPlaceManager().revealCurrentPlace();
        ginjector.getStartupManager().startup();
    }
}
