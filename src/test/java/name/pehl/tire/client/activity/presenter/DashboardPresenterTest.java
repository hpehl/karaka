package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.dispatch.ActivitiesRequest;
import name.pehl.tire.client.activity.dispatch.GetActivitiesAction;
import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetActivitiesResult;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DashboardPresenterTest extends PresenterTest implements ShowMessageHandler, ActivitiesLoadedHandler
{
    // ------------------------------------------------------------------ setup

    static final boolean[][] UPDATE_ACTIVITY_COMBINATIONS = new boolean[][] { {false, false}, {false, true},
            {true, false}, {true, true}};

    GetActivitiesHandler getActivitiesHandler;
    DashboardPresenter.MyView view;
    DashboardPresenter.MyProxy proxy;
    EditActivityPresenter editActivityPresenter;
    SelectYearAndMonthOrWeekPresenter selectYearAndMonthOrWeekPresenter;
    TickCommand tickCommand;
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
        editActivityPresenter = mock(EditActivityPresenter.class);
        selectYearAndMonthOrWeekPresenter = mock(SelectYearAndMonthOrWeekPresenter.class);
        tickCommand = mock(TickCommand.class);
        cut = new DashboardPresenter(eventBus, view, proxy, editActivityPresenter, selectYearAndMonthOrWeekPresenter,
                selectYearAndMonthOrWeekPresenter, newDispatcher(actionHandlerMappings), placeManager, scheduler);
        cut.tickCommand = tickCommand;
    }


    // ------------------------------------------------------------------ tests

    @Test
    @SuppressWarnings("unchecked")
    public void prepareFromRequest()
    {
        // execute place request
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", "2000").with("month", "1");
        cut.prepareFromRequest(placeRequest);
        ShowMessageEvent messageEvent = (ShowMessageEvent) popEvent();
        Message message = messageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Loading activities for 1 / 2000...", message.getText());
        assertFalse(message.isAutoHide());
        assertEquals(1, scheduler.getScheduledCommands().size());

        // GetActivitiesCommand - success
        Activities activities = td.newActivities(MONTH);
        ActivitiesLoadedEvent activitiesLoadedEvent = new ActivitiesLoadedEvent(activities);
        ActivitiesRequest activitiesRequest = new ActivitiesRequest(placeRequest);
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
        assertSame(activities, cut.activities);
        assertNull(cut.runningActivity);
        verifyZeroInteractions(tickCommand);
        verify(view).updateActivities(activities);
        assertEquals(activitiesLoadedEvent, popEvent());

        // GetActivitiesCommand - success & running activity
        reset(view, tickCommand);
        Activity activity = td.newActivity();
        activity.start();
        activities.add(activity);
        cut.new GetActivitiesCommand(activitiesRequest).execute();
        assertSame(activities, cut.activities);
        assertSame(activity, cut.runningActivity);
        verify(tickCommand).update(activity);
        verify(view).updateActivities(activities);
        assertEquals(activitiesLoadedEvent, popEvent());

        // GetActivitiesCommand - 404
        reset(getActivitiesHandler, view, tickCommand);
        Answer<Object> getActivitiesAnswerFail = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetActivitiesResult> callback = (AsyncCallback<GetActivitiesResult>) invocation
                        .getArguments()[1];
                callback.onFailure(new FailedStatusCodeException("foo", 404));
                return null;
            }
        };
        doAnswer(getActivitiesAnswerFail).when(getActivitiesHandler).execute(eq(getActivitiesAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        cut.new GetActivitiesCommand(activitiesRequest).execute();
        messageEvent = (ShowMessageEvent) popEvent();
        message = messageEvent.getMessage();
        assertEquals(WARNING, message.getLevel());
        assertEquals("No activities found for 1 / 2000", message.getText());
        assertTrue(message.isAutoHide());

        // GetActivitiesCommand - exception
        reset(getActivitiesHandler, view, tickCommand);
        getActivitiesAnswerFail = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetActivitiesResult> callback = (AsyncCallback<GetActivitiesResult>) invocation
                        .getArguments()[1];
                callback.onFailure(new Exception("foo"));
                return null;
            }
        };
        doAnswer(getActivitiesAnswerFail).when(getActivitiesHandler).execute(eq(getActivitiesAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        cut.new GetActivitiesCommand(activitiesRequest).execute();
        messageEvent = (ShowMessageEvent) popEvent();
        message = messageEvent.getMessage();
        assertEquals(SEVERE, message.getLevel());
        assertEquals("foo", message.getText());
        assertTrue(message.isAutoHide());
    }


    @Test
    public void onRunningActivityLoaded()
    {
        Activity activity = td.newActivity();
        activity.start();
        RunningActivityLoadedEvent event = new RunningActivityLoadedEvent(activity);
        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            reset(tickCommand, view);
            prepareUpdateActivity(activity, combination[0], combination[1]);
            cut.onRunningActivityLoaded(event);
            assertSame(activity, cut.runningActivity);
            verifyUpdateActivity(activity, combination[0], combination[1]);
            verify(tickCommand).start(activity);
        }
    }


    @Test
    public void onTick()
    {
        Activity activity = td.newActivity();
        activity.start();
        TickEvent event = new TickEvent(activity);
        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            reset(view);
            prepareUpdateActivity(activity, combination[0], combination[1]);
            cut.onTick(event);
            verifyUpdateActivity(activity, combination[0], combination[1]);
        }
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


    // --------------------------------------------------------- helper methods

    void prepareUpdateActivity(Activity activity, boolean matchingRange, boolean contains)
    {
        cut.activities = mock(Activities.class);
        when(cut.activities.matchingRange(activity)).thenReturn(matchingRange);
        when(cut.activities.contains(activity)).thenReturn(contains);
    }


    void verifyUpdateActivity(Activity activity, boolean matchingRange, boolean contains)
    {
        verify(cut.activities).update(activity);
        if (matchingRange)
        {
            verify(cut.activities).add(activity);
        }
        else
        {
            verify(cut.activities, never()).add(activity);
        }
        if (contains)
        {
            verify(view).updateActivities(cut.activities);
        }
        else
        {
            verify(view, never()).updateActivities(cut.activities);
        }
    }
}