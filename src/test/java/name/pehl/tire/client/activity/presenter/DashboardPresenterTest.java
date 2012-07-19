package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.NameTokens.dashboard;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
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

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.dispatch.ActivitiesRequest;
import name.pehl.tire.client.activity.dispatch.DeleteActivityAction;
import name.pehl.tire.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.tire.client.activity.dispatch.DeleteActivityResult;
import name.pehl.tire.client.activity.dispatch.GetActivitiesAction;
import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetActivitiesResult;
import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityHandler;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;
import name.pehl.tire.client.project.LookupProjectPresenterWidget;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.ActivityComparator;

import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class DashboardPresenterTest extends PresenterTest implements ShowMessageHandler, ActivitiesLoadedHandler,
        ActivityChangedHandler
{
    // ------------------------------------------------------------------ setup

    static final boolean[][] UPDATE_ACTIVITY_COMBINATIONS = new boolean[][] { {false, false}, {false, true},
            {true, false}, {true, true}};

    GetActivitiesHandler getActivitiesHandler;
    SaveActivityHandler saveActivityHandler;
    DeleteActivityHandler deleteActivityHandler;
    DashboardPresenter.MyView view;
    DashboardPresenter.MyProxy proxy;
    LookupActivityPresenterWidget lookupActivityPresenter;
    LookupProjectPresenterWidget lookupProjectPresenter;
    SelectMonthPresenter selectMonthPresenter;
    SelectWeekPresenter selectWeekPresenter;
    EditActivityPresenter editActivityPresenter;
    TickCommand tickCommand;
    DashboardPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        getActivitiesHandler = mock(GetActivitiesHandler.class);
        saveActivityHandler = mock(SaveActivityHandler.class);
        deleteActivityHandler = mock(DeleteActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(GetActivitiesAction.class, getActivitiesHandler)
                .put(SaveActivityAction.class, saveActivityHandler)
                .put(DeleteActivityAction.class, deleteActivityHandler).build();

        // class under test
        addEvents(this, ShowMessageEvent.getType(), ActivitiesLoadedEvent.getType(), ActivityChangedEvent.getType());
        view = mock(DashboardPresenter.MyView.class);
        proxy = mock(DashboardPresenter.MyProxy.class);
        lookupActivityPresenter = mock(LookupActivityPresenterWidget.class);
        lookupProjectPresenter = mock(LookupProjectPresenterWidget.class);
        selectMonthPresenter = mock(SelectMonthPresenter.class);
        selectWeekPresenter = mock(SelectWeekPresenter.class);
        editActivityPresenter = mock(EditActivityPresenter.class);
        tickCommand = mock(TickCommand.class);
        cut = new DashboardPresenter(eventBus, view, proxy, lookupActivityPresenter, lookupProjectPresenter,
                selectMonthPresenter, selectWeekPresenter, editActivityPresenter, newDispatcher(actionHandlerMappings),
                placeManager, scheduler);
        cut.tickCommand = tickCommand;
    }


    // ------------------------------------------------------------------ tests

    @Test
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
    }


    @Test
    @SuppressWarnings("unchecked")
    public void getActivities()
    {
        // GetActivitiesCommand - success
        Activities activities = td.newActivities(MONTH);
        ActivitiesLoadedEvent activitiesLoadedEvent = new ActivitiesLoadedEvent(activities);
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", "2000").with("month", "1");
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
        ShowMessageEvent messageEvent = (ShowMessageEvent) popEvent();
        Message message = messageEvent.getMessage();
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


    @Test
    public void onSelectDate()
    {
        Date date = new Date();
        cut.onSelectDate(date);
        assertSame(date, cut.activityDate);
    }


    @Test
    public void onCurrentWeek()
    {
        cut.onCurrentWeek();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("current", "week"));
    }


    @Test
    public void onCurrentMonth()
    {
        cut.onCurrentMonth();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("current", "month"));
    }


    @Test
    public void onSelectMonth()
    {
        SelectTimeUnitPresenter.MyView selectView = mock(SelectTimeUnitPresenter.MyView.class);
        when(selectMonthPresenter.getView()).thenReturn(selectView);
        cut.onSelectMonth(100, 200);
        verify(selectView).setPosition(100, 200);
    }


    @Test
    public void onSelectWeek()
    {
        SelectTimeUnitPresenter.MyView selectView = mock(SelectTimeUnitPresenter.MyView.class);
        when(selectWeekPresenter.getView()).thenReturn(selectView);
        cut.onSelectWeek(100, 200);
        verify(selectView).setPosition(100, 200);
    }


    @Test
    public void onPrevious()
    {
        // decrease month, keep year
        Activities activities = td.newActivities(MONTH);
        activities.setMonth(9);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("month", "8"));

        // decrease month and year
        reset(placeManager);
        activities = td.newActivities(MONTH);
        activities.setMonth(1);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "1999").with("month", "12"));

        // decrease week, keep year
        activities = td.newActivities(WEEK);
        activities.setWeek(11);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("week", "10"));

        // decrease week and year
        reset(placeManager);
        activities = td.newActivities(WEEK);
        activities.setWeek(1);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onPrevious();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "1999").with("week", "52"));
    }


    @Test
    public void onNext()
    {
        // increase month, keep year
        Activities activities = td.newActivities(MONTH);
        activities.setMonth(9);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("month", "10"));

        // increase month and year
        reset(placeManager);
        activities = td.newActivities(MONTH);
        activities.setMonth(12);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2001").with("month", "1"));

        // increase week, keep year
        activities = td.newActivities(WEEK);
        activities.setWeek(11);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2000").with("week", "12"));

        // increase week and year
        reset(placeManager);
        activities = td.newActivities(WEEK);
        activities.setWeek(52);
        activities.setYear(2000);
        cut.activities = activities;
        cut.onNext();
        verify(placeManager).revealPlace(new PlaceRequest(dashboard).with("year", "2001").with("week", "1"));
    }


    @Test
    public void details()
    {
        EditActivityPresenter.MyView editView = mock(EditActivityPresenter.MyView.class);
        when(editActivityPresenter.getView()).thenReturn(editView);
        Activity activity = td.newActivity();
        cut.details(activity);
        verify(editView).setActivity(activity);
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

        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            prepareUpdateActivity(activity, combination[0], combination[1]);
            cut.save(activity);
            verifyUpdateActivity(activity, combination[0], combination[1]);
            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" saved", message.getText());
            assertTrue(message.isAutoHide());
            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), CHANGED);
            assertSame(activityChangedEvent.getActivity(), activity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void copy()
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

        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            prepareUpdateActivity(activity, combination[0], combination[1]);
            cut.copy(activity);
            assertTrue(activity.isStopped());
            verifyUpdateActivity(activity, combination[0], combination[1]);
            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" added", message.getText());
            assertTrue(message.isAutoHide());
            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), NEW);
            assertSame(activityChangedEvent.getActivity(), activity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void resumeActivityNoOtherActivityRunning()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
        SortedSet<Activity> activities = new TreeSet<Activity>(new ActivityComparator());
        activities.add(activity);
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

        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            reset(tickCommand);
            activity.stop();
            prepareUpdateActivity(activity, combination[0], combination[1]);
            when(cut.activities.activities()).thenReturn(activities);
            cut.start(activity);
            assertTrue(activity.isRunning());
            assertSame(cut.runningActivity, activity);
            verifyUpdateActivity(activity, combination[0], combination[1]);
            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" resumed", message.getText());
            assertTrue(message.isAutoHide());
            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), RESUMED);
            assertSame(activityChangedEvent.getActivity(), activity);
            verify(tickCommand).start(activity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void startActivityNoOtherActivityRunning()
    {
        Activity activity = td.newActivity(DateTime.now().minusDays(1).minusHours(1), DateTime.now().minusDays(1));
        activity.setName("Foo");
        SortedSet<Activity> activities = new TreeSet<Activity>(new ActivityComparator());
        activities.add(activity);
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

        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            reset(tickCommand);
            activity.stop();
            prepareUpdateActivity(activity, combination[0], combination[1]);
            when(cut.activities.activities()).thenReturn(activities);
            cut.start(activity);
            assertSame(cut.runningActivity, activity);
            verifyUpdateActivity(activity, combination[0], combination[1]);
            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" started", message.getText());
            assertTrue(message.isAutoHide());
            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), STARTED);
            assertSame(activityChangedEvent.getActivity(), activity);
            verify(tickCommand).start(activity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void resumeActivityOtherActivityRunning()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
        SortedSet<Activity> activities = new TreeSet<Activity>(new ActivityComparator());
        activities.add(activity);
        Activity runningActivity = td.newActivity();
        cut.runningActivity = runningActivity;
        cut.runningActivity.start();

        // save running activity
        SaveActivityAction saveRunningActivityAction = new SaveActivityAction(runningActivity);
        final SaveActivityResult saveRunningActivityResult = new SaveActivityResult(runningActivity);
        Answer<Object> saveRunningActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<SaveActivityResult> callback = (AsyncCallback<SaveActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(saveRunningActivityResult);
                return null;
            }
        };
        doAnswer(saveRunningActivityAnswer).when(saveActivityHandler).execute(eq(saveRunningActivityAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        // save actual activity
        SaveActivityAction saveActualActivityAction = new SaveActivityAction(activity);
        final SaveActivityResult saveActualActivityResult = new SaveActivityResult(activity);
        Answer<Object> saveActualActivityAnswer = new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
            {
                AsyncCallback<SaveActivityResult> callback = (AsyncCallback<SaveActivityResult>) invocation
                        .getArguments()[1];
                callback.onSuccess(saveActualActivityResult);
                return null;
            }
        };
        doAnswer(saveActualActivityAnswer).when(saveActivityHandler).execute(eq(saveActualActivityAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));

        // One combination is enough
        cut.activities = mock(Activities.class);
        when(cut.activities.matchingRange(any(Activity.class))).thenReturn(false);
        when(cut.activities.contains(any(Activity.class))).thenReturn(false);
        when(cut.activities.activities()).thenReturn(activities);

        cut.start(activity);

        // stop running activity
        verify(tickCommand).stop();
        verify(cut.activities).update(runningActivity);

        // start actual activity
        assertTrue(activity.isRunning());
        assertSame(cut.runningActivity, activity);
        verify(cut.activities).update(runningActivity);
        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" resumed", message.getText());
        assertTrue(message.isAutoHide());
        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), RESUMED);
        assertSame(activityChangedEvent.getActivity(), activity);
        verify(tickCommand).start(activity);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void stop()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
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

        for (boolean[] combination : UPDATE_ACTIVITY_COMBINATIONS)
        {
            reset(tickCommand);
            activity.start();
            cut.runningActivity = activity;
            prepareUpdateActivity(activity, combination[0], combination[1]);
            cut.stop(activity);
            verify(tickCommand).stop();
            assertTrue(activity.isStopped());
            assertNull(cut.runningActivity);
            verifyUpdateActivity(activity, combination[0], combination[1]);
            ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
            Message message = showMessageEvent.getMessage();
            assertEquals(INFO, message.getLevel());
            assertEquals("Activity \"Foo\" stopped", message.getText());
            assertTrue(message.isAutoHide());
            ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
            assertEquals(activityChangedEvent.getAction(), STOPPED);
            assertSame(activityChangedEvent.getActivity(), activity);
        }
    }


    @Test
    @SuppressWarnings("unchecked")
    public void delete()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
        DeleteActivityAction deleteActivityAction = new DeleteActivityAction(activity);
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
        doAnswer(deleteActivityAnswer).when(deleteActivityHandler).execute(eq(deleteActivityAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        cut.activities = mock(Activities.class);

        cut.delete(activity);
        verify(cut.activities).remove(activity);
        verify(view).updateActivities(cut.activities);
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
    public void deleteRunningActivity()
    {
        Activity activity = td.newActivity();
        activity.setName("Foo");
        activity.start();
        cut.runningActivity = activity;
        DeleteActivityAction deleteActivityAction = new DeleteActivityAction(activity);
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
        doAnswer(deleteActivityAnswer).when(deleteActivityHandler).execute(eq(deleteActivityAction),
                any(AsyncCallback.class), any(ExecuteCommand.class));
        cut.activities = mock(Activities.class);

        cut.delete(activity);
        assertNull(cut.runningActivity);
        verify(tickCommand).stop();
        verify(cut.activities).remove(activity);
        verify(view).updateActivities(cut.activities);
        ShowMessageEvent showMessageEvent = (ShowMessageEvent) popEvent();
        Message message = showMessageEvent.getMessage();
        assertEquals(INFO, message.getLevel());
        assertEquals("Activity \"Foo\" deleted", message.getText());
        assertTrue(message.isAutoHide());
        ActivityChangedEvent activityChangedEvent = (ActivityChangedEvent) popEvent();
        assertEquals(activityChangedEvent.getAction(), DELETE);
        assertSame(activityChangedEvent.getActivity(), activity);
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


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
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
