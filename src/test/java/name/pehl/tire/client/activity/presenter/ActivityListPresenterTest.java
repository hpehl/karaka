package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.COPY;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.DETAILS;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import org.junit.Before;
import org.junit.Test;

public class ActivityListPresenterTest extends PresenterTest implements ActivityActionHandler
{
    // ------------------------------------------------------------------ setup

    ActivityListPresenter.MyView view;
    EditActivityPresenter editActivityPresenter;
    ActivityListPresenter cut;


    @Before
    public void setUp()
    {
        addEvents(this, ActivityActionEvent.getType());
        view = mock(ActivityListPresenter.MyView.class);
        editActivityPresenter = mock(EditActivityPresenter.class);
        cut = new ActivityListPresenter(eventBus, view, editActivityPresenter);
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
    public void onActivityDetails()
    {
        EditActivityPresenter.MyView editView = mock(EditActivityPresenter.MyView.class);
        when(editActivityPresenter.getView()).thenReturn(editView);
        Activity activity = td.newActivity();
        cut.onActivityAction(DETAILS, activity);
        verify(editView).setActivity(activity);
    }


    @Test
    public void onAnyButDetailsActivityAction()
    {
        Activity activity = td.newActivity();
        cut.onActivityAction(COPY, activity);
        ActivityActionEvent event = (ActivityActionEvent) popEvent();
        assertEquals(COPY, event.getAction());
        assertEquals(activity, event.getActivity());
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        pushEvent(event);
    }
}
