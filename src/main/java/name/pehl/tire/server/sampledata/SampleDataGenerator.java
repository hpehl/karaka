package name.pehl.tire.server.sampledata;

import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.client.control.ClientRepository;
import name.pehl.tire.server.project.control.ProjectRepository;

import com.google.inject.Inject;

public class SampleDataGenerator
{
    @Inject
    ActivityRepository repository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    ClientRepository clientRepository;
}
