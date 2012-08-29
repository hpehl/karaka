package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.activity.dispatch.ActivitiesRequest;
import name.pehl.tire.client.activity.dispatch.TestableActivitiesRequest;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;

import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class TestableDashboardPresenter extends DashboardPresenter
{
    public TestableDashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy, Scheduler scheduler,
            DispatchAsync dispatcher, NewActivityPresenter newActivityPresenter,
            ActivityNavigationPresenter activityNavigationPresenter, ActivityListPresenter activityListPresenter)
    {
        super(eventBus, view, proxy, scheduler, dispatcher, newActivityPresenter, activityNavigationPresenter,
                activityListPresenter);
    }


    @Override
    public void prepareFromRequest(PlaceRequest placeRequest)
    {
        final ActivitiesRequest activitiesRequest = new TestableActivitiesRequest(placeRequest);
        ShowMessageEvent.fire(this, new Message(INFO, "Loading activities for " + activitiesRequest + "...", false));
        scheduler.scheduleDeferred(new GetActivitiesCommand(activitiesRequest));
    }
}
