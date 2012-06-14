package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Day;
import name.pehl.tire.shared.model.Week;

import org.junit.Before;
import org.junit.Test;

public class QuickChartPresenterTest extends PresenterTest
{
    // ------------------------------------------------------------------ setup

    QuickChartPresenter.MyView view;
    QuickChartPresenter cut;


    @Before
    public void setUp()
    {
        view = mock(QuickChartPresenter.MyView.class);
        cut = new QuickChartPresenter(eventBus, view);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onActivitiesLoaded()
    {
        Activities activities = td.newActivities(WEEK);
        cut.onActivitiesLoaded(new ActivitiesLoadedEvent(activities));
        assertSame(cut.activities, activities);
        verify(view).updateActivities(activities);
    }


    @Test
    public void onActivityChanged()
    {
        Week week = new Week();
        ActivityChangedEvent changedEvent = td.newActivityChangedEvent(CHANGED);
        cut.activities = mock(Activities.class);
        when(cut.activities.getUnit()).thenReturn(MONTH);
        when(cut.activities.weekOf(changedEvent.getActivity())).thenReturn(week);
        cut.onActivityChanged(changedEvent);
        verify(view).updateWeek(week);

        reset(view);
        Day day = new Day();
        cut.activities = mock(Activities.class);
        when(cut.activities.getUnit()).thenReturn(WEEK);
        when(cut.activities.dayOf(changedEvent.getActivity())).thenReturn(day);
        cut.onActivityChanged(changedEvent);
        verify(view).updateDay(day);

        reset(view);
        cut.onActivityChanged(td.newActivityChangedEvent(DELETE));
        verify(view).updateActivities(any(Activities.class));
    }


    @Test
    public void onTick()
    {
        Week week = new Week();
        TickEvent tickEvent = td.newTickEvent();
        cut.activities = mock(Activities.class);
        when(cut.activities.getUnit()).thenReturn(MONTH);
        when(cut.activities.weekOf(tickEvent.getActivity())).thenReturn(week);
        cut.onTick(tickEvent);
        verify(view).updateWeek(week);

        reset(view);
        Day day = new Day();
        cut.activities = mock(Activities.class);
        when(cut.activities.getUnit()).thenReturn(WEEK);
        when(cut.activities.dayOf(tickEvent.getActivity())).thenReturn(day);
        cut.onTick(tickEvent);
        verify(view).updateDay(day);
    }
}
