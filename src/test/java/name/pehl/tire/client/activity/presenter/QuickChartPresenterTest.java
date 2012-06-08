package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Stack;

import name.pehl.tire.TestData;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter.MyView;
import name.pehl.tire.shared.model.Activities;

import org.junit.Before;
import org.junit.Test;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.testing.CountingEventBus;

public class QuickChartPresenterTest
{
    // ------------------------------------------------------------------ setup

    TestData td;
    Stack<Event<?>> events;
    CountingEventBus eventBus;
    MyView view;
    QuickChartPresenter cut;


    @Before
    public void setUp()
    {
        td = new TestData();
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        view = mock(MyView.class);
        cut = new QuickChartPresenter(eventBus, view);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onActivitiesLoaded()
    {
        Activities activities = td.newActivities(WEEK);
        ActivitiesLoadedEvent event = new ActivitiesLoadedEvent(activities);
        cut.onActivitiesLoaded(event);
        assertSame(cut.activities, activities);
        verify(view).updateActivities(activities);
    }


    @Test
    public void onActivityChanged()
    {
        cut.onActivityChanged(td.newActivityChangedEvent(CHANGED));
        verify(view).updateActivities(any(Activities.class));
    }


    @Test
    public void onTick()
    {
        cut.onTick(td.newTickEvent());
        verify(view).updateActivities(any(Activities.class));
    }
}
