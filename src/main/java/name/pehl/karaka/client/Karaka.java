package name.pehl.karaka.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import name.pehl.karaka.client.bootstrap.BootstrapProcess;
import name.pehl.karaka.client.gin.KarakaGinjector;

import static name.pehl.karaka.client.logging.Logger.Category.bootstrap;
import static name.pehl.karaka.client.logging.Logger.fatal;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 95 $
 */
public class Karaka implements EntryPoint
{
    public final KarakaGinjector ginjector = GWT.create(KarakaGinjector.class);

    @Override
    public void onModuleLoad()
    {
        // Defer all application initialisation code to onModuleLoad2() so that the
        // UncaughtExceptionHandler can catch any unexpected exceptions.
        Log.setUncaughtExceptionHandler();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                onModuleLoad2();
            }
        });
    }

    private void onModuleLoad2()
    {
        ginjector.getResources().karaka().ensureInjected();
        ginjector.getResources().widgets().ensureInjected();

        GWT.runAsync(new RunAsyncCallback()
        {
            @Override
            public void onFailure(final Throwable reason)
            {
                Window.alert("Failed to load Karaka");
                fatal(bootstrap, "Failed to load Karaka", reason);
            }

            @Override
            public void onSuccess()
            {
                DelayedBindRegistry.bind(ginjector);
                BootstrapProcess bootstrapProcess = ginjector.getBootstrapProcess();
                bootstrapProcess.execute(new AsyncCallback<Boolean>()
                {
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        fatal(bootstrap, "Error in bootstrap process", caught);
                    }

                    @Override
                    public void onSuccess(Boolean wasSuccessfull)
                    {
                        if (!wasSuccessfull)
                        {
                            fatal(bootstrap, "Bootstrap process was not successfull");
                        }
                    }
                });
            }
        });
    }
}
