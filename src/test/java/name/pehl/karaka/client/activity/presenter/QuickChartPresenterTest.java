package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.karaka.shared.model.TimeUnit.MONTH;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import name.pehl.karaka.client.PresenterTest;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;

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
        cut = new QuickChartPresenter(eventBus, view, placeManager);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onActivitiesLoaded()
    {
        Activities activities = td.newActivities(WEEK);
        cut.onActivitiesLoaded(new ActivitiesLoadedEvent(activities));
        verify(view).updateActivities(activities);
    }


    @Test
    public void onActivityChanged()
    {
        Activity activity = td.newActivity();
        Activities activities = td.newActivities(MONTH);
        activities.add(activity);
        cut.onActivityChanged(td.newActivityChangedEvent(CHANGED, activity, activities));
        verify(view).updateActivities(activities);

        reset(view);
        activities = td.newActivities(WEEK);
        activities.add(activity);
        cut.onActivityChanged(td.newActivityChangedEvent(CHANGED, activity, activities));
        verify(view).updateActivities(activities);

        reset(view);
        cut.onActivityChanged(td.newActivityChangedEvent(DELETE));
        verify(view).updateActivities(any(Activities.class));
    }


    @Test
    public void onTick()
    {
        Activity activity = td.newActivity();
        Activities activities = td.newActivities(MONTH);
        activities.add(activity);
        cut.onTick(td.newTickEvent(activity, activities));
        verify(view).updateActivities(activities);

        reset(view);
        activities = td.newActivities(WEEK);
        activities.add(activity);
        cut.onTick(td.newTickEvent(activity, activities));
        verify(view).updateActivities(activities);
    }
}
