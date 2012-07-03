package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.SAVE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.shared.model.Activity;

import org.junit.Before;
import org.junit.Test;

public class EditActivityPresenterTest extends PresenterTest implements ActivityActionHandler
{
    // ------------------------------------------------------------------ setup

    EditActivityPresenter.MyView view;
    EditActivityPresenter cut;


    @Before
    public void setUp()
    {
        addEvents(this, ActivityActionEvent.getType());
        view = mock(EditActivityPresenter.MyView.class);
        cut = new EditActivityPresenter(eventBus, view);
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onSave()
    {
        Activity activity = td.newActivity();
        cut.onSave(activity);
        ActivityActionEvent event = (ActivityActionEvent) popEvent();
        assertEquals(SAVE, event.getAction());
        assertEquals(activity, event.getActivity());
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        pushEvent(event);
    }
}
