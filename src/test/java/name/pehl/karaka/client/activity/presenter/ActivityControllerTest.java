package name.pehl.karaka.client.activity.presenter;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityAction;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityResult;
import name.pehl.karaka.client.activity.dispatch.SaveActivityAction;
import name.pehl.karaka.client.activity.dispatch.SaveActivityHandler;
import name.pehl.karaka.client.activity.dispatch.SaveActivityResult;
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
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.logging.Level.INFO;
import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.*;
import static name.pehl.karaka.shared.model.Status.RUNNING;
import static name.pehl.karaka.shared.model.Status.STOPPED;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@Ignore("Needs refactoring")
public class ActivityControllerTest extends PresenterTest implements TickHandler, ActivityChangedHandler,
        ShowMessageHandler

{
    // ------------------------------------------------------------------ setup

    static final boolean[][] UPDATE_ACTIVITIES_COMBINATIONS = new boolean[][] { {false, false}, {false, true},
            {true, false}, {true, true}};

    SaveActivityHandler saveActivityHandler;
    DeleteActivityHandler deleteActivityHandler;
    ActivityController cut;


    @Before
    public void setUp()
    {
        // client action handlers
        saveActivityHandler = mock(SaveActivityHandler.class);
        deleteActivityHandler = mock(DeleteActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(SaveActivityAction.class, saveActivityHandler)
                .put(DeleteActivityAction.class, deleteActivityHandler).build();

        // class under test
        addEvents(this, TickEvent.getType(), ActivityChangedEvent.getType(), ShowMessageEvent.getType());
        cut = new ActivityController(eventBus, scheduler, newDispatcher(actionHandlerMappings));
        cut.start();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onRunningActivityLoaded()
    {
        Activity activity = td.newActivity();
        activity.setStatus(RUNNING);
        cut.onRunningActivityLoaded(new RunningActivityLoadedEvent(activity));
        assertSame(activity, cut.runningActivity);
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
        Activity activityToSave = td.newActivity();
        activityToSave.setName("Foo");
        Activity savedActivity = activityToSave.copy();

        final SaveActivityResult saveActivityResult = new SaveActivityResult(savedActivity);
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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            prepareUpdateActivities(activityToSave, savedActivity, combination[0], combination[1]);
            cut.save(activityToSave);
            verifyUpdateActivities(activityToSave, savedActivity, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" saved", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), CHANGED);
            assertSame(activityChangedEvent.getActivity(), savedActivity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void copy()
    {
        Activity activityToCopy = td.newActivity();
        activityToCopy.setName("Foo");
        Activity copiedActivity = activityToCopy.copy();

        final SaveActivityResult saveActivityResult = new SaveActivityResult(copiedActivity);
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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            prepareUpdateActivities(null, copiedActivity, combination[0], combination[1]);
            cut.copy(activityToCopy);
            verifyUpdateActivities(null, copiedActivity, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" added", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), NEW);
            assertSame(activityChangedEvent.getActivity(), copiedActivity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void startActivityNoOtherActivityRunning()
    {
        // create a *transient* activity!
        Activity activityToStart = new Activity("Test activity");
        activityToStart.setStart(td.newTime(DateTime.now().minusDays(1).minusHours(1)));
        activityToStart.setEnd(td.newTime(DateTime.now()));
        activityToStart.setName("Foo");
        Activity startedActivity = activityToStart.copy();
        startedActivity.setStatus(RUNNING);

        final SaveActivityResult saveActivityResult = new SaveActivityResult(startedActivity);
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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            activityToStart.setStatus(STOPPED);
            cut.ticking = false;
            cut.runningActivity = null;
            scheduler.getRepeatingCommands().clear();

            prepareUpdateActivities(activityToStart, startedActivity, combination[0], combination[1]);
            cut.start(activityToStart);
            assertSame(cut.runningActivity, startedActivity);
            verifyUpdateActivities(activityToStart, startedActivity, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" started", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), STARTED);
            assertSame(activityChangedEvent.getActivity(), startedActivity);

            assertTrue(cut.ticking);
            assertEquals(1, scheduler.getRepeatingCommands().size());
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void resumeActivityNoOtherActivityRunning()
    {
        // create a *transient* activity!
        Activity activityToResume = new Activity("Test activity");
        activityToResume.setName("Foo");
        Activity resumedActivity = activityToResume.copy();
        resumedActivity.setStatus(RUNNING);

        final SaveActivityResult saveActivityResult = new SaveActivityResult(resumedActivity);
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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            activityToResume.setStatus(STOPPED);
            cut.ticking = false;
            cut.runningActivity = null;
            scheduler.getRepeatingCommands().clear();

            prepareUpdateActivities(activityToResume, resumedActivity, combination[0], combination[1]);
            when(cut.activities.activities()).thenReturn(new TreeSet<Activity>(asList(activityToResume)));
            cut.start(activityToResume);
            assertSame(cut.runningActivity, resumedActivity);
            verifyUpdateActivities(activityToResume, resumedActivity, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" resumed", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), RESUMED);
            assertSame(activityChangedEvent.getActivity(), resumedActivity);

            assertTrue(cut.ticking);
            assertEquals(1, scheduler.getRepeatingCommands().size());
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void stop()
    {
        Activity activityToStop = td.newActivity();
        activityToStop.setName("Foo");
        Activity stoppedActivity = activityToStop.copy();

        final SaveActivityResult saveActivityResult = new SaveActivityResult(stoppedActivity);
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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            activityToStop.setStatus(RUNNING);
            cut.runningActivity = activityToStop;

            prepareUpdateActivities(activityToStop, stoppedActivity, combination[0], combination[1]);
            cut.stop(activityToStop);
            assertFalse(cut.ticking);
            assertTrue(activityToStop.isStopped());
            assertNull(cut.runningActivity);
            verifyUpdateActivities(activityToStop, stoppedActivity, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" stopped", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), ActivityChanged.ChangeAction.STOPPED);
            assertSame(activityChangedEvent.getActivity(), stoppedActivity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void delete()
    {
        Activity activityToDelete = td.newActivity();
        activityToDelete.setName("Foo");

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

        for (boolean[] combination : UPDATE_ACTIVITIES_COMBINATIONS)
        {
            prepareUpdateActivities(activityToDelete, null, combination[0], combination[1]);
            cut.delete(activityToDelete);
            verifyUpdateActivities(activityToDelete, null, combination[0], combination[1]);

            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" deleted", message.getText());
            assertTrue(message.isAutoHide());

            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), DELETE);
            assertSame(activityChangedEvent.getActivity(), activityToDelete);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void onExecuteTick()
    {
        Activities activities = td.newActivities(WEEK);
        Activity activity = td.newActivity();
        activity.setStatus(RUNNING);
        activities.add(activity);
        cut.ticking = true;
        cut.runningActivity = activity;
        cut.activities = activities;

        SaveActivityAction saveActivityAction = new SaveActivityAction(activity);
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
        doAnswer(saveActivityAnswer).when(saveActivityHandler).execute(eq(saveActivityAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        cut.execute();
        assertSame(activity, cut.runningActivity);
        TickEvent event = (TickEvent) popEvent();
        assertSame(activity, event.getActivity());
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onTick(TickEvent event)
    {
        pushEvent(event);
    }


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        pushEvent(event);
    }


    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        pushEvent(event);
    }


    // --------------------------------------------------------- helper methods

    void prepareUpdateActivities(Activity activityBefore, Activity activityAfter, boolean contains,
            boolean matchingRange)
    {
        cut.activities = mock(Activities.class);
        if (activityBefore != null)
        {
            when(cut.activities.contains(activityBefore)).thenReturn(contains);
        }
        if (activityAfter != null)
        {
            when(cut.activities.matchingRange(activityAfter)).thenReturn(matchingRange);
        }
    }


    void verifyUpdateActivities(Activity activityBefore, Activity activityAfter, boolean contains, boolean matchingRange)
    {
        if (contains && activityBefore != null)
        {
            verify(cut.activities).remove(activityBefore);
        }
        else
        {
            verify(cut.activities, never()).remove(activityBefore);
        }
        if (matchingRange && activityAfter != null)
        {
            verify(cut.activities).add(activityAfter);
        }
        else
        {
            verify(cut.activities, never()).add(activityAfter);
        }
    }
}
