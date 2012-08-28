package name.pehl.tire.client;

import static org.mockito.Mockito.mock;

import java.util.Map;
import java.util.Stack;

import name.pehl.tire.TestData;

import org.junit.After;
import org.junit.Before;

import com.google.gwt.core.client.testing.StubScheduler;
import com.google.gwt.event.shared.EventHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.server.Dispatch;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.tester.TestDispatchAsync;
import com.gwtplatform.tester.TestDispatchService;

public abstract class PresenterTest extends GwtTest
{
    protected TestData td;
    protected Stack<Event<?>> events;
    protected CountingEventBus eventBus;
    protected StubScheduler scheduler;
    protected PlaceManager placeManager;


    @Before
    public void presenterTestSetUp()
    {
        td = new TestData();
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        scheduler = new StubScheduler();
        placeManager = mock(PlaceManager.class);
    }


    @After
    public void presenterTestSetUpTearDown()
    {
        events.clear();
    }


    protected DispatchAsync newDispatcher(Map<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings)
    {
        Dispatch dispatch = mock(Dispatch.class);
        Injector injector = Guice.createInjector(new MockClientHandlerModule(actionHandlerMappings));
        DispatchAsync dispatcher = new TestDispatchAsync(new TestDispatchService(dispatch), injector);
        return dispatcher;

    }


    /**
     * Method to add events <em>fired</em> by the class under test
     * 
     * @param handler
     * @param types
     */
    @SuppressWarnings("unchecked")
    protected void addEvents(EventHandler handler, Type<?>... types)
    {
        for (Type<?> type : types)
        {
            eventBus.addHandler((Type<EventHandler>) type, handler);
        }
    }


    protected void pushEvent(Event<?> event)
    {
        events.push(event);
    }


    protected Event<?> popEvent()
    {
        return events.pop();
    }
}
