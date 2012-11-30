package name.pehl.karaka.client.dispatch;

import com.google.gwt.inject.client.AbstractGinModule;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

public class DispatchModule extends AbstractGinModule
{
    @Override
    protected void configure()
    {
        install(new DispatchAsyncModule.Builder().clientActionHandlerRegistry(KarakaActionHandlerRegistry.class).build());
    }
}
