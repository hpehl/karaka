package name.pehl.karaka.client.about;

import com.google.inject.Inject;

import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.application.ApplicationPresenter;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 22:35:01 +0100 (Mi, 01. Dez 2010) $ $Revision: 85
 *          $
 */
public class AboutPresenter extends Presenter<AboutPresenter.MyView, AboutPresenter.MyProxy>
{
    @ProxyCodeSplit
    @NameToken(NameTokens.about)
    public interface MyProxy extends ProxyPlace<AboutPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public AboutPresenter(EventBus eventBus, MyView view, MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_MainContent, this);
    }
}
