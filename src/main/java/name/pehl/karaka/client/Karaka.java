package name.pehl.karaka.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import name.pehl.karaka.client.gin.KarakaGinjector;

import static name.pehl.karaka.client.logging.Logger.Category.bootstrap;
import static name.pehl.karaka.client.logging.Logger.info;

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
        // Defer all application initialisation code to so that the
        // UncaughtExceptionHandler can catch any unexpected exceptions.
        Log.setUncaughtExceptionHandler();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                DelayedBindRegistry.bind(ginjector);
                ginjector.getBootstrapProcess().start(new Command()
                {
                    @Override
                    public void execute()
                    {
                        info(bootstrap, "Bootstrap process finished");
                    }
                });
            }
        });
    }
}
