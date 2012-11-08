package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.LinkedList;

import static name.pehl.karaka.client.logging.Logger.Category.bootstrap;
import static name.pehl.karaka.client.logging.Logger.fatal;
import static name.pehl.karaka.client.logging.Logger.info;

public class BootstrapProcess
{
    private int index;
    private final LinkedList<BootstrapCommand> commands;

    @Inject
    public BootstrapProcess(LoadClients loadClients, LoadMainApp loadMainApp,
            LoadProjects loadProjects, LoadSettings loadSettings, LoadTags loadTags)
    {
        // add commands - order is important!
        this.commands = new LinkedList<BootstrapCommand>();
        this.commands.add(loadMainApp);
        this.commands.add(loadSettings);
        this.commands.add(loadClients);
        this.commands.add(loadProjects);
        this.commands.add(loadTags);
    }

    public void execute(AsyncCallback<Boolean> outcome)
    {
        index = 0;
        executeNext(outcome);
    }

    private void executeNext(final AsyncCallback<Boolean> outcome)
    {
        if (index < commands.size())
        {
            final BootstrapCommand nextComand = commands.get(index);
            index++;

            String status = index + ": " + nextComand.getClass().getName();
            info(bootstrap, status);
            Window.setStatus(status);
            nextComand.execute(new AsyncCallback<Boolean>()
            {
                @Override
                public void onFailure(Throwable caught)
                {
                    fatal(bootstrap, "Failed to execute bootstrap command " + nextComand.getClass().getName());
                }

                @Override
                public void onSuccess(Boolean successful)
                {
                    if (successful)
                    {
                        info(bootstrap, "Bootstrap command " + nextComand.getClass().getName() + " finished");
                        executeNext(outcome);
                    }
                    else
                    {
                        fatal(bootstrap, "Bootstrap command " + nextComand.getClass().getName() + " returned false");
                        outcome.onSuccess(Boolean.FALSE);
                    }
                }
            });
        }
        outcome.onSuccess(Boolean.TRUE);
        Window.setStatus("");
    }
}
