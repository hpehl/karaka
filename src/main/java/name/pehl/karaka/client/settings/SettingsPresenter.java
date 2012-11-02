package name.pehl.karaka.client.settings;

import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.application.ApplicationPresenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class SettingsPresenter extends Presenter<SettingsPresenter.MyView, SettingsPresenter.MyProxy>
{
    @ProxyCodeSplit
    @NameToken(NameTokens.settings)
    public interface MyProxy extends ProxyPlace<SettingsPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public SettingsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
}
