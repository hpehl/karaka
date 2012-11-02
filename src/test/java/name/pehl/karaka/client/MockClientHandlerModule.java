package name.pehl.karaka.client;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.internal.UniqueAnnotations;
import com.gwtplatform.dispatch.client.actionhandler.ClientActionHandler;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.tester.MockClientActionHandlerMap;
import com.gwtplatform.tester.MockHandlerModule;

public class MockClientHandlerModule extends MockHandlerModule
{
    private static class MockClientActionHandlerMapImpl<A extends Action<R>, R extends Result> implements
            MockClientActionHandlerMap
    {
        private final Class<A> actionClass;
        private final ClientActionHandler<A, R> clientActionHandler;


        @SuppressWarnings("unchecked")
        public MockClientActionHandlerMapImpl(Class<?> actionClass, ClientActionHandler<?, ?> clientActionHandler)
        {
            this.actionClass = (Class<A>) actionClass;
            this.clientActionHandler = (ClientActionHandler<A, R>) clientActionHandler;
        }


        @Override
        public Class<A> getActionClass()
        {
            return actionClass;
        }


        @Override
        public ClientActionHandler<A, R> getClientActionHandler()
        {
            return clientActionHandler;
        }
    }

    private Map<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings;


    public MockClientHandlerModule(Map<Class<?>, ClientActionHandler<?, ?>> actionHandlerMappings)
    {
        this.actionHandlerMappings = actionHandlerMappings;
    }


    public MockClientHandlerModule add(Class<?> actionClass, ClientActionHandler<?, ?> handler)
    {
        actionHandlerMappings.put(actionClass, handler);
        return this;
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void configure()
    {
        for (Entry<Class<?>, ClientActionHandler<?, ?>> entry : actionHandlerMappings.entrySet())
        {
            this.<Action, Result, ClientActionHandler> internalBind(entry.getKey(), entry.getValue());
        }
    }


    @Override
    protected void configureMockHandlers()
    {
    }


    private <A extends Action<R>, R extends Result, H extends ClientActionHandler<A, R>> void internalBind(
            Class<?> actionClass, ClientActionHandler<?, ?> handler)
    {
        bind(MockClientActionHandlerMap.class).annotatedWith(UniqueAnnotations.create()).toInstance(
                new MockClientActionHandlerMapImpl<A, R>(actionClass, handler));
    }
}
