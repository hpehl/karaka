package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.shared.model.Activities;

import org.junit.Before;
import org.junit.Test;

import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import static org.junit.Assert.assertSame;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
