package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BootstrapProcess
{
    private final List<BootstrapStep> steps;


    @Inject
    public BootstrapProcess(LoadClients loadClients, LoadMainApp loadMainApp, LoadProjects loadProjects,
            LoadSettings loadSettings, LoadTags loadTags)
    {
        // add steps - order is important!
        this.steps = new LinkedList<BootstrapStep>();
        this.steps.add(loadMainApp);
        this.steps.add(loadSettings);
        this.steps.add(loadClients);
        this.steps.add(loadProjects);
        this.steps.add(loadTags);
    }

    public void start(final Command command)
    {
        Iterator<BootstrapStep> iterator = steps.iterator();
        BootstrapStep first = iterator.next();
        first.execute(iterator, command);
    }
}
