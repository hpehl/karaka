package name.pehl.tire.client.report;

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
public class ReportPresenter extends Presenter<ReportPresenter.MyView, ReportPresenter.MyProxy>
{
    @ProxyCodeSplit
    @NameToken(NameTokens.reports)
    public interface MyProxy extends ProxyPlace<ReportPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public ReportPresenter(EventBus eventBus, MyView view, MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
}
