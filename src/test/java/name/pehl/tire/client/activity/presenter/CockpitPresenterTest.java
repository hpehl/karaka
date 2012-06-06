package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import java.util.Stack;

import name.pehl.tire.TestData;
import name.pehl.tire.client.activity.dispatch.GetMinutesAction;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.dispatch.GetMinutesResult;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityAction;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityResult;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.tire.client.activity.presenter.CockpitPresenter.GetMinutesCommand;
import name.pehl.tire.client.activity.presenter.CockpitPresenter.GetRunningActivityCommand;
import name.pehl.tire.client.activity.presenter.CockpitPresenter.MyView;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.tire.shared.model.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.client.testing.StubScheduler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.dispatch.server.Dispatch;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.tester.MockHandlerModule;
import com.gwtplatform.tester.TestDispatchAsync;
import com.gwtplatform.tester.TestDispatchService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
public class CockpitPresenterTest implements ShowMessageHandler, ActivityActionHandler, RunningActivityLoadedHandler
{
    // ------------------------------------------------------------------ setup

    TestData td;
    Stack<Event<?>> events;
    CountingEventBus eventBus;
    MyView view;
    Dispatch dispatch;
    GetMinutesHandler getMinutesHandler;
    GetRunningActivityHandler getRunningActivityHandler;
    DispatchAsync dispatcher;
    StubScheduler scheduler;
    CockpitPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        getMinutesHandler = mock(GetMinutesHandler.class);
        getRunningActivityHandler = mock(GetRunningActivityHandler.class);
        Injector injector = Guice.createInjector(new MockHandlerModule()
        {
            @Override
            protected void configure()
            {
                bindMockClientActionHandler(GetMinutesAction.class, getMinutesHandler);
                bindMockClientActionHandler(GetRunningActivityAction.class, getRunningActivityHandler);
            }


            @Override
            protected void configureMockHandlers()
            {
            }
        });

        // static mocks
        mockStatic(GWT.class);
        mockStatic(URL.class);
        when(GWT.getHostPageBaseURL()).thenReturn("http://localhost");
        when(URL.encode(Mockito.anyString())).thenAnswer(new Answer<String>()
        {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable
            {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });

        // actual setup
        td = new TestData();
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        eventBus.addHandler(ShowMessageEvent.getType(), this);
        eventBus.addHandler(ActivityActionEvent.getType(), this);
        eventBus.addHandler(RunningActivityLoadedEvent.getType(), this);
        view = mock(MyView.class);
        dispatch = mock(Dispatch.class);
        dispatcher = new TestDispatchAsync(new TestDispatchService(dispatch), injector);
        scheduler = new StubScheduler();
        cut = new CockpitPresenter(eventBus, view, dispatcher, scheduler);
    }


    @After
    public void tearDown()
    {
        events.clear();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onReveal()
    {
        cut.onReveal();
        List<ScheduledCommand> commands = scheduler.getScheduledCommands();
        assertEquals(2, commands.size());
        assertTrue(commands.get(0) instanceof GetMinutesCommand);
        assertTrue(commands.get(1) instanceof GetRunningActivityCommand);
    }


    @Test
    public void onStartStopNoCurrentActivity()
    {
        cut.onStartStop();
        Message message = ((ShowMessageEvent) events.pop()).getMessage();
        assertEquals(WARNING, message.getLevel());
        assertTrue(message.isAutoHide());
    }


    @Test
    public void onStartStopWithCurrentActivity()
    {
        cut.currentActivity = td.newActivity();
        cut.onStartStop();
        ActivityActionEvent event = (ActivityActionEvent) events.pop();
        assertEquals(START_STOP, event.getAction());
        assertEquals(cut.currentActivity, event.getActivity());
    }


    @Test
    public void onNewActivity()
    {
        prepareGetMinutes();
        Activity activity = td.newActivity();
        cut.currentActivity = activity;
        cut.onActivityChanged(td.newActivityChangedEvent(NEW));
        assertEquals(activity, cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();
    }


    @Test
    public void onResumedStartedStopppedActivity()
    {
        prepareGetMinutes();
        ActivityChangedEvent event = td.newActivityChangedEvent(RESUMED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        prepareGetMinutes();
        event = td.newActivityChangedEvent(STARTED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        prepareGetMinutes();
        event = td.newActivityChangedEvent(STOPPED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();
    }


    @Test
    public void onDeleteCurrentActivity()
    {
        prepareGetMinutes();
        Activity activity = td.newActivity();
        cut.currentActivity = activity;
        cut.onActivityChanged(td.newActivityChangedEvent(DELETE, activity));
        assertNull(cut.currentActivity);
        verify(view).updateStatus(null);
        verifyGetMinutes();
    }


    @Test
    public void onDeleteSomeActivity()
    {
        prepareGetMinutes();
        Activity activity = td.newActivity();
        cut.currentActivity = activity;
        cut.onActivityChanged(td.newActivityChangedEvent(DELETE));
        assertEquals(activity, cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();
    }


    @Test
    public void onTick()
    {
        prepareGetMinutes();
        cut.onTick(td.newTickEvent());
        verifyGetMinutes();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void getRunningActivity()
    {
        Activity activity = td.newActivity();
        final GetRunningActivityResult getRunningActivityResult = new GetRunningActivityResult(activity);
        Answer<Object> getRunningActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetRunningActivityResult> callback = (AsyncCallback<GetRunningActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(getRunningActivityResult);
                return null;
            }
        };
        doAnswer(getRunningActivityAnswer).when(getRunningActivityHandler).execute(any(GetRunningActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.getRunningActivityCommand.execute();
        assertEquals(activity, cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        RunningActivityLoadedEvent event = (RunningActivityLoadedEvent) events.pop();
        assertEquals(activity, event.getActivity());
    }


    @Test
    @SuppressWarnings("unchecked")
    public void faultyGetMinutes()
    {
        Answer<Object> getMinutesAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetMinutesResult> callback = (AsyncCallback<GetMinutesResult>) invocation.getArguments()[1];
                callback.onFailure(null);
                return null;
            }
        };
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/currentMonth/minutes/")),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/currentWeek/minutes/")),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/today/minutes/")), any(AsyncCallback.class),
                any(ExecuteCommand.class));

        cut.getMinutesCommand.execute();
        verify(view).updateMonth(0);
        verify(view).updateWeek(0);
        verify(view).updateToday(0);
    }


    // --------------------------------------------------------- helper methods

    @SuppressWarnings("unchecked")
    void prepareGetMinutes()
    {
        final GetMinutesResult getMinutesResult = new GetMinutesResult(42);
        Answer<Object> getMinutesAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<GetMinutesResult> callback = (AsyncCallback<GetMinutesResult>) invocation.getArguments()[1];
                callback.onSuccess(getMinutesResult);
                return null;
            }
        };
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/currentMonth/minutes/")),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/currentWeek/minutes/")),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(
                eq(new GetMinutesAction("http://localhost/rest/activities/today/minutes/")), any(AsyncCallback.class),
                any(ExecuteCommand.class));
    }


    void verifyGetMinutes()
    {
        verify(view).updateMonth(42);
        verify(view).updateWeek(42);
        verify(view).updateToday(42);
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        events.push(event);
    }


    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        events.push(event);
    }


    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        events.push(event);
    }
}
