package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import name.pehl.tire.server.project.entity.Project;

class ProjectProducer
{
    @Inject IdGenerator idGenerator;

    @Inject RandomString randomString;


    @Produces
    @Count
    public List<Project> produceProjects(InjectionPoint ip)
    {
        List<Project> projects = new ArrayList<Project>();
        Count count = ip.getAnnotated().getAnnotation(Count.class);
        if (count != null && count.value() > 0)
        {
            for (int i = 0; i < count.value(); i++)
            {
                Project project = new Project("Project " + randomString.next(5), randomString.next(10));
                projects.add(project);
            }
        }
        return projects;
    }
}
