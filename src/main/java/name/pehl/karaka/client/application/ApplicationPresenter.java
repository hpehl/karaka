package name.pehl.karaka.client.application;

import name.pehl.karaka.client.activity.presenter.CockpitPresenter;
import name.pehl.karaka.client.activity.presenter.QuickChartPresenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * This is the top-level presenter of the hierarchy. Other presenters reveal
 * themselves within this presenter. Therefore they must use the
 * {@link #TYPE_MainContent} slot.
 * <p>
 * Besides this slot the presenter contains the following nested
 * {@link PresenterWidget}s:
 * <ul>
 * <li>{@link NavigationPresenter} in slot {@link #TYPE_Navigation}
 * <li>{@link CockpitPresenter} in slot {@link #TYPE_Cockpit}
 * <li>{@link QuickChartPresenter} in slot {@link #TYPE_QuickChart}
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17. Dez 2010) $ $Revision: 102
 *          $
 */
public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {}
    public interface MyView extends View {}

    @ContentSlot public static final Type<RevealContentHandler<?>> TYPE_MainContent = new Type<RevealContentHandler<?>>();
    public static final Object TYPE_Navigation = new Object();
    public static final Object TYPE_Cockpit = new Object();
    public static final Object TYPE_QuickChart = new Object();

    final NavigationPresenter navigationPresenter;
    final CockpitPresenter cockpitPresenter;
    final QuickChartPresenter quickChartPresenter;

    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final NavigationPresenter navigationPresenter, final CockpitPresenter cockpitPresenter,
            final QuickChartPresenter quickChartPresenter) {
        super(eventBus, view, proxy, RevealType.Root);
        this.navigationPresenter = navigationPresenter;
        this.cockpitPresenter = cockpitPresenter;
        this.quickChartPresenter = quickChartPresenter;
    }

    @Override
    protected void onBind() {
        super.onBind();
        setInSlot(TYPE_Navigation, navigationPresenter);
        setInSlot(TYPE_Cockpit, cockpitPresenter);
        setInSlot(TYPE_QuickChart, quickChartPresenter);
    }
}
