package name.pehl.tire.client.tag;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.application.ApplicationPresenter;

import com.google.web.bindery.event.shared.EventBus;
import com.google.inject.Inject;
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
public class TagPresenter extends Presenter<TagPresenter.MyView, TagPresenter.MyProxy>
{
    @ProxyCodeSplit
    @NameToken(NameTokens.tags)
    public interface MyProxy extends ProxyPlace<TagPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public TagPresenter(EventBus eventBus, MyView view, MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
}
