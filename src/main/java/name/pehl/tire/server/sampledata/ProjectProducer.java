package name.pehl.tire.server.sampledata;

import javax.inject.Inject;

import name.pehl.tire.server.project.entity.Project;

public class ProjectProducer
{
    @Inject
    IdGenerator idGenerator;

    @Inject
    RandomString randomString;


    public Project newProject()
    {
        Project project = new Project("Project " + randomString.next(5), randomString.next(10));
        project.setId(idGenerator.nextId());
        return project;
    }
}
