package name.pehl.tire.client;

import java.util.Map;
import java.util.Stack;

import name.pehl.tire.TestData;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.testing.StubScheduler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.http.client.URL;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
public abstract class PresenterTest
{
    protected TestData td;
    protected Stack<Event<?>> events;
    protected CountingEventBus eventBus;
    protected StubScheduler scheduler;
    protected PlaceManager placeManager;


    @Before
    public void presenterTestSetUp()
    {
        mockStatic(GWT.class);
        mockStatic(URL.class);
        when(GWT.getHostPageBaseURL()).thenReturn("http://localhost");
        when(URL.encode(Mockito.anyString())).thenAnswer(new Answer<String>()
        {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable
            {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });

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
