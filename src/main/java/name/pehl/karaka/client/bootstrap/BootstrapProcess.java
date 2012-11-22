package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static name.pehl.karaka.client.logging.Logger.Category.bootstrap;
import static name.pehl.karaka.client.logging.Logger.info;

public class BootstrapProcess
{
    private final List<BootstrapStep> steps;


    @Inject
    public BootstrapProcess(LoadClients loadClients, LoadProjects loadProjects, LoadSettings loadSettings,
            LoadTags loadTags)
    {
        // add steps - order is important!
        this.steps = new LinkedList<BootstrapStep>();
        this.steps.add(loadSettings);
        this.steps.add(loadClients);
        this.steps.add(loadProjects);
        this.steps.add(loadTags);
    }

    public void execute(final AsyncCallback<Boolean> outcome)
    {
        final Iterator<BootstrapStep> iterator = steps.iterator();
        final BootstrapStep first = iterator.next();

        info(bootstrap, "Execute: " + first.getClass().getName());
        first.execute(iterator, outcome);
    }
}
