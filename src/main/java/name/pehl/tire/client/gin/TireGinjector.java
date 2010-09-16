package name.pehl.tire.client.gin;

import name.pehl.tire.client.application.ApplicationPresenter;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GinModules(TireModule.class)
public interface TireGinjector extends Ginjector
{
    ApplicationPresenter getApplicationPresenter();


    PlaceManager getPlaceManager();
}
