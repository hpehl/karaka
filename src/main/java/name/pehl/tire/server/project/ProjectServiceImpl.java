package name.pehl.tire.server.project;

import java.util.List;

import name.pehl.tire.server.model.Project;
import name.pehl.tire.server.persistence.TransactionTemplate;

import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ProjectServiceImpl implements ProjectService
{
    private final ProjectDao projectDao;
    @SuppressWarnings("unused")
    private final ActivityDao activityDao;


    @Inject
    public ProjectServiceImpl(ProjectDao projectDao, ActivityDao activityDao)
    {
        this.projectDao = projectDao;
        this.activityDao = activityDao;
    }


    @Override
    public List<Project> list()
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
