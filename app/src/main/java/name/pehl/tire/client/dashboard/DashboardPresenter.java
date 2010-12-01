package name.pehl.tire.client.dashboard;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.NewActivityPresenter;
import name.pehl.tire.client.activity.RecentActivitiesPresenter;
import name.pehl.tire.client.application.ApplicationPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author$
 * @version $Date$ $Revision: 85
 *          $
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

    /**
     * Constant for the status slot.
     */
    public static final Object SLOT_NewActivity = new Object();
    /**
     * Constant for the status slot.
     */
    public static final Object SLOT_RecentActivities = new Object();

    private final NewActivityPresenter newActivityPresenter;
    private final RecentActivitiesPresenter recentActivitiesPresenter;


    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy,
            final NewActivityPresenter newActivityPresenter, final RecentActivitiesPresenter recentActivitiesPresenter)
    {
        super(eventBus, view, proxy);
        this.newActivityPresenter = newActivityPresenter;
        this.recentActivitiesPresenter = recentActivitiesPresenter;
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    /**
     * Sets the {@link NewActivityPresenter} to slot {@link #SLOT_NewActivity}
     * and {@link RecentActivitiesPresenter} to slot
     * {@link #SLOT_RecentActivities}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        setInSlot(SLOT_NewActivity, newActivityPresenter);
        setInSlot(SLOT_RecentActivities, recentActivitiesPresenter);
    }
}
