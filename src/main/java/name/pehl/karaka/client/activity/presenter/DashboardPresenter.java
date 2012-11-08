package name.pehl.karaka.client.activity.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import name.pehl.karaka.client.activity.dispatch.ActivitiesRequest;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesAction;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesResult;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import org.fusesource.restygwt.client.FailedStatusCodeException;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.warn;

/**
 * <p>
 * The main presenter in Karaka. This presenter is responsible to start, resume
 * and stop activities. Other presenters are notified with appropriate events.
 * </p>
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain ShowMessageEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>{@linkplain GetActivitiesAction}
 * </ul>
 *
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy>
{
    // ---------------------------------------------------------- inner classes

    @ProxyStandard
    @NameToken(dashboard)
    public interface MyProxy extends ProxyPlace<DashboardPresenter>
    {
    }

    public interface MyView extends View
    {
    }

    // ------------------------------------------------------- (static) members

    public static final Object SLOT_NewActivity = new Object();
    public static final Object SLOT_ActivityNavigation = new Object();
    public static final Object SLOT_ActivityList = new Object();

    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final NewActivityPresenter newActivityPresenter;
    final ActivityNavigationPresenter activityNavigationPresenter;
    final ActivityListPresenter activityListPresenter;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final Scheduler scheduler, final DispatchAsync dispatcher, final NewActivityPresenter newActivityPresenter,
            final ActivityNavigationPresenter activityNavigationPresenter,
            final ActivityListPresenter activityListPresenter)
    {
        super(eventBus, view, proxy);
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.newActivityPresenter = newActivityPresenter;
        this.activityNavigationPresenter = activityNavigationPresenter;
        this.activityListPresenter = activityListPresenter;
    }


    // ---------------------------------------------------- presenter lifecycle

    /**
     * Turns the parameters in the place request into an
     * {@link ActivitiesRequest} instance and calls {@link GetActivitiesAction}.
     *
     * @param placeRequest
     * @see com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform.mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(final PlaceRequest placeRequest)
    {
        super.prepareFromRequest(placeRequest);
        final ActivitiesRequest activitiesRequest = new ActivitiesRequest(placeRequest);
        ShowMessageEvent.fire(this, new Message(INFO, "Loading activities for " + activitiesRequest + "...", false));
        scheduler.scheduleDeferred(new GetActivitiesCommand(activitiesRequest));
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        setInSlot(SLOT_NewActivity, newActivityPresenter);
        setInSlot(SLOT_ActivityNavigation, activityNavigationPresenter);
        setInSlot(SLOT_ActivityList, activityListPresenter);
    }


    @Override
    protected void onHide()
    {
        super.onHide();
        removeFromSlot(SLOT_NewActivity, newActivityPresenter);
        removeFromSlot(SLOT_ActivityNavigation, activityNavigationPresenter);
        removeFromSlot(SLOT_ActivityList, activityListPresenter);
    }

    // --------------------------------------------------- commands & callbacks

    class GetActivitiesCommand implements ScheduledCommand
    {
        final ActivitiesRequest activitiesRequest;


        GetActivitiesCommand(final ActivitiesRequest activitiesRequest)
        {
            this.activitiesRequest = activitiesRequest;
        }


        @Override
        public void execute()
        {
            dispatcher.execute(new GetActivitiesAction(activitiesRequest), new KarakaCallback<GetActivitiesResult>(
                    getEventBus())
            {
                @Override
                public void onSuccess(final GetActivitiesResult result)
                {
                    ActivitiesLoadedEvent.fire(DashboardPresenter.this, result.getActivities());
                }


                @Override
                public void onNotFound(final FailedStatusCodeException caught)
                {
                    String errorMessage = "No activities found for " + activitiesRequest;
                    ShowMessageEvent.fire(DashboardPresenter.this, new Message(WARNING, errorMessage, true));
                    warn(activity, errorMessage);
                }
            });
        }
    }
}
