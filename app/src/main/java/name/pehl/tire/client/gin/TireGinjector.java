package name.pehl.tire.client.gin;

import name.pehl.tire.client.TirePresenter;
import name.pehl.tire.client.dashboard.DashboardPresenter;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

/**
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
@GinModules(TireModule.class)
public interface TireGinjector extends Ginjector
{
    EventBus getEventBus();


    Provider<TirePresenter> getTirePresenter();


    Provider<DashboardPresenter> getDashboardPresenter();


    PlaceManager getPlaceManager();


    ProxyFailureHandler getProxyFailureHandler();
}
