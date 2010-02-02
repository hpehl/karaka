package name.pehl.tire.server.project;

import java.util.Iterator;

import name.pehl.tire.server.persistence.TransactionTemplate;
import name.pehl.tire.shared.project.Project;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectServiceImpl implements ProjectService
{
    private final ProjectDao projectDao;
    private final ActivityDao activityDao;


    @Inject
    public ProjectServiceImpl(ProjectDao projectDao, ActivityDao activityDao)
    {
        this.projectDao = projectDao;
        this.activityDao = activityDao;
    }


    @Override
    public Iterator<Project> list()
    {
        return projectDao.list();
    }


    @Override
    public void save(final Project project)
    {
        new TransactionTemplate()
        {
            @Override
            protected void perform()
            {
                projectDao.put(project);
            }
        }.run();
    }
}
