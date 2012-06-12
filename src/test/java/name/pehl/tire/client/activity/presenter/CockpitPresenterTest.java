package name.pehl.tire.client.activity.presenter;

import java.util.List;

import name.pehl.tire.client.PresenterTest;
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
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Minutes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;

import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class CockpitPresenterTest extends PresenterTest implements ShowMessageHandler, ActivityActionHandler,
        RunningActivityLoadedHandler
{
    // ------------------------------------------------------------------ setup

    GetMinutesHandler getMinutesHandler;
    GetRunningActivityHandler getRunningActivityHandler;
    CockpitPresenter.MyView view;
    CockpitPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        getMinutesHandler = mock(GetMinutesHandler.class);
        getRunningActivityHandler = mock(GetRunningActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(GetMinutesAction.class, getMinutesHandler)
                .put(GetRunningActivityAction.class, getRunningActivityHandler).build();

        // class under test
        addEvents(this, ShowMessageEvent.getType(), ActivityActionEvent.getType(), RunningActivityLoadedEvent.getType());
        view = mock(CockpitPresenter.MyView.class);
        cut = new CockpitPresenter(eventBus, view, newDispatcher(actionHandlerMappings), scheduler);
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
        ShowMessageEvent event = (ShowMessageEvent) popEvent();
        Message message = event.getMessage();
        assertEquals(WARNING, message.getLevel());
        assertTrue(message.isAutoHide());
    }


    @Test
    public void onStartStopWithCurrentActivity()
    {
        cut.currentActivity = td.newActivity();
        cut.onStartStop();
        ActivityActionEvent event = (ActivityActionEvent) popEvent();
        assertEquals(START_STOP, event.getAction());
        assertSame(cut.currentActivity, event.getActivity());
    }


    @Test
    public void onNewActivity()
    {
        prepareGetMinutes();
        Activity activity = td.newActivity();
        cut.currentActivity = activity;
        cut.onActivityChanged(td.newActivityChangedEvent(NEW));
        assertSame(activity, cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();
    }


    @Test
    public void onResumedStartedStopppedActivity()
    {
        prepareGetMinutes();
        ActivityChangedEvent event = td.newActivityChangedEvent(RESUMED);
        cut.onActivityChanged(event);
        assertSame(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        prepareGetMinutes();
        event = td.newActivityChangedEvent(STARTED);
        cut.onActivityChanged(event);
        assertSame(event.getActivity(), cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        verifyGetMinutes();

        reset(view, getMinutesHandler);
        prepareGetMinutes();
        event = td.newActivityChangedEvent(STOPPED);
        cut.onActivityChanged(event);
        assertSame(event.getActivity(), cut.currentActivity);
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
        assertSame(activity, cut.currentActivity);
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

        assertSame(activity, cut.currentActivity);
        verify(view).updateStatus(cut.currentActivity);
        RunningActivityLoadedEvent event = (RunningActivityLoadedEvent) popEvent();
        assertSame(activity, event.getActivity());
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
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(any(GetMinutesAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.getMinutesCommand.execute();

        verify(view).updateMinutes(new Minutes());
    }


    // --------------------------------------------------------- helper methods

    @SuppressWarnings("unchecked")
    void prepareGetMinutes()
    {
        final GetMinutesResult getMinutesResult = new GetMinutesResult(new Minutes(1, 2, 3));
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
        doAnswer(getMinutesAnswer).when(getMinutesHandler).execute(any(GetMinutesAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));
    }


    void verifyGetMinutes()
    {
        verify(view).updateMinutes(new Minutes(1, 2, 3));
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        pushEvent(event);
    }


    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        pushEvent(event);
    }


    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        pushEvent(event);
    }
}
