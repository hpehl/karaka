package name.pehl.tire.client.activity.presenter;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Stack;

import name.pehl.tire.TestData;
import name.pehl.tire.client.activity.dispatch.GetMinutesAction;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityAction;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsAction;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekPresenter.MyView;
import name.pehl.tire.client.application.ShowMessageEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.testing.StubScheduler;
import com.google.gwt.http.client.URL;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.testing.CountingEventBus;
import com.gwtplatform.dispatch.server.Dispatch;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.tester.MockHandlerModule;
import com.gwtplatform.tester.TestDispatchAsync;
import com.gwtplatform.tester.TestDispatchService;

RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
ppublic class SelectYearAndMonthOrWeekPresenterTest
{
    // ------------------------------------------------------------------ setup

    TestData td;
    Stack<Event<?>> events;
    CountingEventBus eventBus;
    MyView view;
    Dispatch dispatch;
    GetYearsHandler getYearsHandler;
    DispatchAsync dispatcher;
    SelectYearAndMonthOrWeekPresenter cut;

    @Before
    public void setUp()
    {
        // client action handlers
        getYearsHandler = mock(GetYearsHandler.class);
        Injector injector = Guice.createInjector(new MockHandlerModule()
        {
            @Override
            protected void configure()
            {
                bindMockClientActionHandler(GetYearsAction.class, getYearsHandler);
            }


            @Override
            protected void configureMockHandlers()
            {
            }
        });

        // static mocks
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

        // actual setup
        td = new TestData();
        events = new Stack<Event<?>>();
        eventBus = new CountingEventBus();
        eventBus.addHandler(ShowMessageEvent.getType(), this);
        eventBus.addHandler(ActivityActionEvent.getType(), this);
        eventBus.addHandler(RunningActivityLoadedEvent.getType(), this);
        view = mock(MyView.class);
        dispatch = mock(Dispatch.class);
        dispatcher = new TestDispatchAsync(new TestDispatchService(dispatch), injector);
        scheduler = new StubScheduler();
        cut = new CockpitPresenter(eventBus, view, dispatcher, scheduler);
    }

    // ------------------------------------------------------------------ tests

    @Test
    public void test()
    {
        fail("Not yet implemented");
    }
}
