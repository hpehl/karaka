package name.pehl.tire.client.gin;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.TirePlaceManager;
import name.pehl.tire.client.TirePresenter;
import name.pehl.tire.client.TireView;
import name.pehl.tire.client.dashboard.DashboardPresenter;
import name.pehl.tire.client.dashboard.DashboardView;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.status.StatusPresenter;
import name.pehl.tire.client.status.StatusView;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultEventBus;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
public class TireModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        // Singletons
        bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).to(TirePlaceManager.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();
        bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class).in(Singleton.class);
        bind(I18n.class).in(Singleton.class);
        bind(Resources.class).in(Singleton.class);

        // Rest
        // TODO bind(ProjectClient.class).in(Singleton.class);

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.dashboard);

        // MVP
        bindPresenter(TirePresenter.class, TirePresenter.MyView.class, TireView.class, TirePresenter.MyProxy.class);
        bindPresenter(DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class,
                DashboardPresenter.MyProxy.class);
        bindPresenterWidget(StatusPresenter.class, StatusPresenter.MyView.class, StatusView.class);
    }
}
