package name.pehl.karaka.client.activity.presenter;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.dispatch.ActivitiesRequest;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesAction;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesResult;
import name.pehl.karaka.client.activity.dispatch.TestableActivitiesRequest;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.karaka.shared.model.Activities;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static java.util.logging.Level.INFO;
import static name.pehl.karaka.shared.model.TimeUnit.MONTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class DashboardPresenterTest extends PresenterTest implements ShowMessageHandler, ActivitiesLoadedHandler
{
    // ------------------------------------------------------------------ setup

    GetActivitiesHandler getActivitiesHandler;
    DashboardPresenter.MyView view;
    DashboardPresenter.MyProxy proxy;
    NewActivityPresenter newActivityPresenter;
    ActivityNavigationPresenter activityNavigationPresenter;
    ActivityListPresenter activityListPresenter;
    DashboardPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        getActivitiesHandler = mock(GetActivitiesHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(GetActivitiesAction.class, getActivitiesHandler).build();

        // class under test
        addEvents(this, ShowMessageEvent.getType(), ActivitiesLoadedEvent.getType());
        view = mock(DashboardPresenter.MyView.class);
        proxy = mock(DashboardPresenter.MyProxy.class);
        newActivityPresenter = mock(NewActivityPresenter.class);
        activityNavigationPresenter = mock(ActivityNavigationPresenter.class);
        activityListPresenter = mock(ActivityListPresenter.class);
        cut = new TestableDashboardPresenter(eventBus, view, proxy, scheduler, newDispatcher(actionHandlerMappings),
                newActivityPresenter, activityNavigationPresenter, activityListPresenter);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void prepareFromRequest()
    {
        // start place request
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("path", "2000/1");
        cut.prepareFromRequest(placeRequest);
        ShowMessageEvent messageEvent = (ShowMessageEvent) popEvent();
        Message message = messageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Loading activities...", message.getText());
        assertFalse(message.isAutoHide());
        assertEquals(1, scheduler.getScheduledCommands().size());
    }


    @Test
    @SuppressWarnings("unchecked")
    public void getActivities()
    {
        // GetActivitiesCommand - success
        Activities activities = td.newActivities(MONTH);
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("path", "2000/1");
        ActivitiesRequest activitiesRequest = new TestableActivitiesRequest(placeRequest);
        GetActivitiesAction getActivitiesAction = new GetActivitiesAction(activitiesRequest);
        final GetActivitiesResult getActivitiesResult = new GetActivitiesResult(activities);
        Answer<Object> getActivitiesAnswerSuccess = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetActivitiesResult> callback = (AsyncCallback<GetActivitiesResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(getActivitiesResult);
                return null;
            }
        };
        doAnswer(getActivitiesAnswerSuccess).when(getActivitiesHandler).execute(eq(getActivitiesAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        cut.new GetActivitiesCommand(activitiesRequest).execute();
        ActivitiesLoadedEvent event = (ActivitiesLoadedEvent) popEvent();
        assertEquals(activities, event.getActivities());
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        pushEvent(event);
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        pushEvent(event);
    }
}
