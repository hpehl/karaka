package name.pehl.tire.client.dashboard;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.TirePresenter;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy>
{
    @ProxyStandard
    @NameToken(NameTokens.dashboard)
    public interface MyProxy extends ProxyPlace<DashboardPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, TirePresenter.TYPE_SetMainContent, this);
    }
}
