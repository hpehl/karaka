package name.pehl.tire.server.sampledata;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import name.pehl.tire.server.project.entity.Project;

class ProjectProducer
{
    static String[] names = new String[] {"TiRe", "Piriti", "Totoe", "Guano", "Foo Bar"};
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
