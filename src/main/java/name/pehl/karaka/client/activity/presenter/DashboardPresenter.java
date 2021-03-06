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
import name.pehl.karaka.client.activity.dispatch.ActivitiesRequest;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesAction;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesResult;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesNotFoundEvent;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.dispatch.RestException;
import name.pehl.karaka.shared.model.LinkHeader;
import name.pehl.karaka.shared.model.LinksParser;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.warn;

/**
 * <p>
 * The main presenter in Karaka. This presenter is responsible to init, resume
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
 * <li>{@linkplain ActivitiesNotFoundEvent}</li>
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

    public static final Object TYPE_NewActivity = new Object();
    public static final Object TYPE_ActivityNavigation = new Object();
    public static final Object TYPE_ActivityList = new Object();

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
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_MainContent);
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.newActivityPresenter = newActivityPresenter;
        this.activityNavigationPresenter = activityNavigationPresenter;
        this.activityListPresenter = activityListPresenter;
    }


    // ---------------------------------------------------- presenter lifecycle


    @Override
    protected void onBind()
    {
        super.onBind();
        setInSlot(TYPE_NewActivity, newActivityPresenter);
        setInSlot(TYPE_ActivityNavigation, activityNavigationPresenter);
        setInSlot(TYPE_ActivityList, activityListPresenter);
    }

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
        ShowMessageEvent.fire(this, new Message(INFO, "Loading activities...", false));
        scheduler.scheduleDeferred(new GetActivitiesCommand(activitiesRequest));
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
                    ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activities successfully loaded.", true));
                    ActivitiesLoadedEvent.fire(DashboardPresenter.this, result.getActivities());
                }


                @Override
                public void onNotFound(final RestException caught)
                {
                    String errorMessage = "No activities found";
                    ShowMessageEvent.fire(DashboardPresenter.this, new Message(WARNING, errorMessage, true));
                    warn(activity, errorMessage);

                    String links = caught.getMethod().getResponse().getHeader("Link");
                    LinkHeader linkHeader = new LinkHeader(LinksParser.valueOf(links));
                    ActivitiesNotFoundEvent.fire(DashboardPresenter.this, linkHeader);
                }
            });
        }
    }
}
