package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.WARNING;
import static org.junit.Assert.*;

import java.util.Stack;

import name.pehl.tire.client.activity.presenter.CockpitPresenter.MyView;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.testing.StubScheduler;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

@RunWith(MockitoJUnitRunner.class)
public class CockpitPresenterTest implements ShowMessageHandler
{
    // ------------------------------------------------------------------ setup

    @Mock MyView view;
    @Mock DispatchAsync dispatcher;

    Stack<Event<?>> events;
    CountingEventBus eventBus;
    Scheduler scheduler;
    CockpitPresenter cut;


    @Before
    public void setUp()
    {
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        eventBus.addHandler(ShowMessageEvent.getType(), this);
        scheduler = new StubScheduler();
        cut = new CockpitPresenter(eventBus, view, dispatcher, scheduler);
    }


    @After
    public void tearDown()
    {
        events.clear();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void onStartStop()
    {
        cut.onStartStop();
        assertFalse(events.isEmpty());
        Message message = ((ShowMessageEvent) events.pop()).getMessage();
        assertEquals(WARNING, message.getLevel());
        assertTrue(message.isAutoHide());
    }


    @Test
    public void onActivityChanged()
    {
    }


    @Test
    public void onTick()
    {
    }


    // ----------------------------------------------------------------- events

    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        events.push(event);
    }
}
