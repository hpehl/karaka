package name.pehl.karaka.server.sampledata;

import name.pehl.karaka.server.project.entity.Project;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

class ProjectProducer
{
    static String[] names = new String[] {"Karaka", "Piriti", "Totoe", "Guano", "Foo Bar"};
    @Inject LoremIpsum loremIpsum;


    @Produces
    public List<Project> produceProjects()
    {
        List<Project> projects = new ArrayList<Project>();
        for (String name : names)
        {
            Project project = new Project(name, loremIpsum.randomWords(3));
            projects.add(project);
        }
        return projects;
    }
}
