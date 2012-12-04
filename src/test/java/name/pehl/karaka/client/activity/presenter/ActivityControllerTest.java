package name.pehl.karaka.client.activity.presenter;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.dispatch.CopyActivityAction;
import name.pehl.karaka.client.activity.dispatch.CopyActivityHandler;
import name.pehl.karaka.client.activity.dispatch.CopyActivityResult;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityAction;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityResult;
import name.pehl.karaka.client.activity.dispatch.SaveActivityAction;
import name.pehl.karaka.client.activity.dispatch.SaveActivityHandler;
import name.pehl.karaka.client.activity.dispatch.SaveActivityResult;
import name.pehl.karaka.client.activity.dispatch.StartActivityAction;
import name.pehl.karaka.client.activity.dispatch.StartActivityHandler;
import name.pehl.karaka.client.activity.dispatch.StartActivityResult;
import name.pehl.karaka.client.activity.dispatch.StopActivityAction;
import name.pehl.karaka.client.activity.dispatch.StopActivityHandler;
import name.pehl.karaka.client.activity.dispatch.StopActivityResult;
import name.pehl.karaka.client.activity.dispatch.TickActivityAction;
import name.pehl.karaka.client.activity.dispatch.TickActivityHandler;
import name.pehl.karaka.client.activity.dispatch.TickActivityResult;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivityChanged;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.karaka.client.project.RefreshProjectsEvent;
import name.pehl.karaka.client.tag.RefreshTagsEvent;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.logging.Level.INFO;
import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.*;
import static name.pehl.karaka.client.project.RefreshProjectsEvent.RefreshProjectsHandler;
import static name.pehl.karaka.client.tag.RefreshTagsEvent.RefreshTagsHandler;
import static name.pehl.karaka.shared.model.Status.RUNNING;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

@Ignore("Needs refactoring")
public class ActivityControllerTest extends PresenterTest
        implements ActivityChangedHandler, RefreshProjectsHandler, RefreshTagsHandler, ShowMessageHandler, TickHandler

{
    // ------------------------------------------------------------------ setup

    CopyActivityHandler copyActivityHandler;
    DeleteActivityHandler deleteActivityHandler;
    SaveActivityHandler saveActivityHandler;
    StartActivityHandler startActivityHandler;
    StopActivityHandler stopActivityHandler;
    TickActivityHandler tickActivityHandler;
    ActivityController cut;

    @Before
    public void setUp()
    {
        // client action handlers
        copyActivityHandler = mock(CopyActivityHandler.class);
        deleteActivityHandler = mock(DeleteActivityHandler.class);
        saveActivityHandler = mock(SaveActivityHandler.class);
        startActivityHandler = mock(StartActivityHandler.class);
        stopActivityHandler = mock(StopActivityHandler.class);
        tickActivityHandler = mock(TickActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(CopyActivityAction.class, copyActivityHandler)
                .put(DeleteActivityAction.class, deleteActivityHandler)
                .put(SaveActivityAction.class, saveActivityHandler)
                .put(StartActivityAction.class, startActivityHandler)
                .put(StopActivityAction.class, stopActivityHandler)
                .put(TickActivityAction.class, tickActivityHandler)
                .build();

        // fired events
        addEvents(this, ActivityChangedEvent.getType(), RefreshProjectsEvent.getType(), RefreshTagsEvent.getType(),
                ShowMessageEvent.getType(), TickEvent.getType());
        cut = new ActivityController(eventBus, scheduler, newDispatcher(actionHandlerMappings));
        cut.activities = td.newActivities(WEEK);
        cut.init();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onRunningActivityLoaded()
    {
        Activity activity = td.newActivity();
        activity.setStatus(RUNNING);
        cut.onRunningActivityLoaded(new RunningActivityLoadedEvent(activity));

        assertTrue(cut.ticking);
        assertEquals(1, scheduler.getRepeatingCommands().size());
        assertTick(activity);
    }

    @Test
    public void onActivitiesLoaded()
    {
        Activities activities = td.newActivities(WEEK);
        cut.onActivitiesLoaded(new ActivitiesLoadedEvent(activities));
        assertSame(activities, cut.activities);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void save()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");

        final SaveActivityResult saveActivityResult = new SaveActivityResult(activity);
        Answer<Object> saveActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<SaveActivityResult> callback = (AsyncCallback<SaveActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(saveActivityResult);
                return null;
            }
        };
        doAnswer(saveActivityAnswer).when(saveActivityHandler).execute(any(SaveActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.save(activity);

        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" saved", message.getText());
        assertTrue(message.isAutoHide());

        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), CHANGED);
        assertSame(activityChangedEvent.getActivity(), activity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void copy()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");

        final CopyActivityResult copyActivityResult = new CopyActivityResult(activity);
        Answer<Object> copyActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<CopyActivityResult> callback = (AsyncCallback<CopyActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(copyActivityResult);
                return null;
            }
        };
        doAnswer(copyActivityAnswer).when(copyActivityHandler).execute(any(CopyActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.copy(activity);

        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" added", message.getText());
        assertTrue(message.isAutoHide());

        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), NEW);
        assertSame(activityChangedEvent.getActivity(), activity);
    }

    @Test
    public void startRunningActivity()
    {
        Activity activity = new Activity();
        activity.setStatus(RUNNING);
        cut.start(activity);
        assertTrue(events.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void startActivity()
    {
        Activity activity = new Activity();
        activity.setName("Foo");
        Activity copy = activity.copy();
        copy.setStatus(RUNNING);

        final StartActivityResult startActivityResult = new StartActivityResult(new HashSet<Activity>(asList(copy)));
        Answer<Object> startActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<StartActivityResult> callback = (AsyncCallback<StartActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(startActivityResult);
                return null;
            }
        };
        doAnswer(startActivityAnswer).when(startActivityHandler).execute(any(StartActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.start(activity);

        assertTrue(cut.ticking);
        assertSame(cut.runningActivity, copy);
        assertEquals(1, scheduler.getRepeatingCommands().size());

        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" started", message.getText());
        assertTrue(message.isAutoHide());

        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), STARTED);
        assertSame(activityChangedEvent.getActivity(), copy);
    }

    @Test
    public void stopStoppedActivity()
    {
        Activity activity = td.newActivity();
        cut.stop(activity);
        assertTrue(events.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void stopActivityWhichIsNotTheRunningActivity()
    {
        Activity activity = td.newActivity();
        activity.setStatus(RUNNING);
        cut.stop(activity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void stop()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
        activity.setStatus(RUNNING);
        cut.runningActivity = activity;

        final StopActivityResult stopActivityResult = new StopActivityResult(activity);
        Answer<Object> stopActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<StopActivityResult> callback = (AsyncCallback<StopActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(stopActivityResult);
                return null;
            }
        };
        doAnswer(stopActivityAnswer).when(stopActivityHandler).execute(any(StopActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.stop(activity);

        assertFalse(cut.ticking);
        assertNull(cut.runningActivity);

        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" stopped", message.getText());
        assertTrue(message.isAutoHide());

        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), ActivityChanged.ChangeAction.STOPPED);
        assertSame(activityChangedEvent.getActivity(), activity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void delete()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");

        final DeleteActivityResult deleteActivityResult = new DeleteActivityResult();
        Answer<Object> deleteActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<DeleteActivityResult> callback = (AsyncCallback<DeleteActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(deleteActivityResult);
                return null;
            }
        };
        doAnswer(deleteActivityAnswer).when(deleteActivityHandler).execute(any(DeleteActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.delete(activity);

        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" deleted", message.getText());
        assertTrue(message.isAutoHide());

        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), DELETE);
        assertSame(activityChangedEvent.getActivity(), activity);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void onExecuteTick()
    {
        cut.ticking = true;
        Activity activity = td.newActivity();
        activity.setStatus(RUNNING);
        assertTick(activity);
    }

    @SuppressWarnings("unchecked")
    private void assertTick(final Activity activity)
    {
        final TickActivityResult tickActivityResult = new TickActivityResult(new HashSet<Activity>(asList(activity)));
        Answer<Object> tickActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<TickActivityResult> callback = (AsyncCallback<TickActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(tickActivityResult);
                return null;
            }
        };
        doAnswer(tickActivityAnswer).when(tickActivityHandler).execute(any(TickActivityAction.class),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.execute();

        assertSame(activity, cut.runningActivity);
        TickEvent event = (TickEvent) popEvent();
        assertSame(activity, event.getActivity());
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        pushEvent(event);
    }

    @Override
    public void onRefreshProjects(final RefreshProjectsEvent event)
    {
        pushEvent(event);
    }

    @Override
    public void onRefreshTags(final RefreshTagsEvent event)
    {
        pushEvent(event);
    }

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        pushEvent(event);
    }

    @Override
    public void onTick(TickEvent event)
    {
        pushEvent(event);
    }
}
