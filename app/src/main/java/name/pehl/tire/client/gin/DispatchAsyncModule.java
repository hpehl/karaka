package name.pehl.tire.client.gin;

import name.pehl.tire.client.command.impl.CreateItemActionHandler;
import name.pehl.tire.client.command.impl.GetAllItemsActionHandler;
import name.pehl.tire.client.dispatch.DispatchAsyncRequestBuilderImpl;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.client.DispatchAsync;

public class DispatchAsyncModule extends AbstractGinModule
{
    @Override
    protected void configure()
    {
    }


    @Provides
    @Singleton
    protected DispatchAsync provideDispatchAsync()
    {
        final DispatchAsyncRequestBuilderImpl dispatch = new DispatchAsyncRequestBuilderImpl();

        dispatch.addHandler(new CreateItemActionHandler());
        dispatch.addHandler(new GetAllItemsActionHandler());

        return dispatch;
    }
}
