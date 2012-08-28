package name.pehl.tire.client.activity.presenter;

import static org.mockito.Mockito.mock;
import name.pehl.tire.client.PresenterTest;
import name.pehl.tire.client.activity.dispatch.FindActivityAction;
import name.pehl.tire.client.activity.dispatch.FindActivityHandler;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;

public class NewActivityPresenterTest extends PresenterTest implements ShowMessageHandler
{
    // ------------------------------------------------------------------ setup

    FindActivityHandler findActivityHandler;
    NewActivityPresenter.MyView view;
    NewActivityPresenter cut;


    @Before
    public void setUp()
    {
        // client action handlers
        findActivityHandler = mock(FindActivityHandler.class);
        ImmutableMap<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings = new ImmutableMap.Builder<Class<?>, ClientActionHandler<?, ?>>()
                .put(FindActivityAction.class, findActivityHandler).build();

        // class under test
        addEvents(this, ShowMessageEvent.getType());
        view = mock(NewActivityPresenter.MyView.class);
        cut = new NewActivityPresenter(eventBus, view, newDispatcher(actionHandlerMappings));
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onFindActivity()
    {
        // TODO
    }


    @Test
    public void onActivitySelected()
    {
        // TODO
    }


    @Test
    public void onActivityEntered()
    {
        // TODO
    }


    @Test
    public void onProjectSelected()
    {
        // TODO
    }


    @Test
    public void onProjectEntered()
    {
        // TODO
    }


    @Test
    public void onDurationEntered()
    {
        // TODO
    }


    @Test
    public void onNewActivity()
    {
        // TODO
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        pushEvent(event);
    }
}
