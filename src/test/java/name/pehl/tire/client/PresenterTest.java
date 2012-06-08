package name.pehl.tire.client;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gwtplatform.dispatch.client.actionhandler.AbstractClientActionHandler;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.tester.MockHandlerModule;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GWT.class, URL.class})
public class PresenterTest
{
    @Before
    public void setUp()
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
    }


    protected <A extends Action<R>, R extends Result, H extends AbstractClientActionHandler<A, R>> Injector setUpClientHandlersHandlers(
            final Map<Class<A>, H> handlers)
    {
        Injector injector = Guice.createInjector(new MockHandlerModule()
        {
            @Override
            protected void configure()
            {
                for (Entry<Class<A>, H> entry : handlers.entrySet())
                {
                    bindMockClientActionHandler(entry.getKey(), entry.getValue());

                }
            }


            @Override
            protected void configureMockHandlers()
            {
            }
        });
        return injector;
    }
}
