package name.pehl.tire.client.activity.presenter;

import java.util.List;
import java.util.Stack;

import name.pehl.tire.TestData;
import name.pehl.tire.client.activity.dispatch.GetMinutesAction;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
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
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.dispatch.server.Dispatch;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.tester.MockHandlerModule;
import com.gwtplatform.tester.TestDispatchAsync;
import com.gwtplatform.tester.TestDispatchService;

import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
public class CockpitPresenterTest implements ShowMessageHandler, ActivityActionHandler
{
    // ------------------------------------------------------------------ setup

    TestData td;
    Stack<Event<?>> events;
    CountingEventBus eventBus;
    MyView view;
    Dispatch dispatch;
    GetMinutesHandler getMinutesHandler;
    DispatchAsync dispatcher;
    StubScheduler scheduler;
    CockpitPresenter cut;


    @Before
    public void setUp()
    {
        getMinutesHandler = mock(GetMinutesHandler.class);
        Injector injector = Guice.createInjector(new MockHandlerModule()
        {
            @Override
            protected void configure()
            {
                bindMockClientActionHandler(GetMinutesAction.class, getMinutesHandler);
            }


            @Override
            protected void configureMockHandlers()
            {
            }
        });

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

        td = new TestData();
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        eventBus.addHandler(ShowMessageEvent.getType(), this);
        eventBus.addHandler(ActivityActionEvent.getType(), this);
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
        ActivityChangedEvent event = td.newActivityChangedEvent(RESUMED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        event = td.newActivityChangedEvent(STARTED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        event = td.newActivityChangedEvent(STOPPED);
        cut.onActivityChanged(event);
        assertEquals(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();
    }


    @Test
    public void onDeleteCurrentActivity()
    {
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
        cut.onTick(td.newTickEvent());
        verifyGetMinutes();
    }


    // --------------------------------------------------------- helper methods

    @SuppressWarnings("unchecked")
    protected void verifyGetMinutes()
    {
        // verify(dispatcher).execute(eq(new
        // GetMinutesAction("http://localhost/rest/activities/currentMonth/minutes/")),
        // any(AsyncCallback.class));
        // verify(dispatcher).execute(eq(new
        // GetMinutesAction("http://localhost/rest/activities/currentWeek/minutes/")),
        // any(AsyncCallback.class));
        // verify(dispatcher).execute(eq(new
        // GetMinutesAction("http://localhost/rest/activities/today/minutes/")),
        // any(AsyncCallback.class));
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
}
