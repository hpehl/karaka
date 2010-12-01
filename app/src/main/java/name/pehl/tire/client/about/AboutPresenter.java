package name.pehl.tire.client.about;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.application.ApplicationPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author$
 * @version $Date$ $Revision: 85
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
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
}
