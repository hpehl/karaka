package name.pehl.tire.server.persistence;

import name.pehl.tire.server.project.ActivityDao;
import name.pehl.tire.server.project.ProjectDao;
import name.pehl.tire.server.project.ProjectService;
import name.pehl.tire.server.project.ProjectServiceImpl;

import com.google.inject.AbstractModule;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class PersistenceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ActivityDao.class);
        bind(ProjectDao.class);
        bind(ProjectService.class).to(ProjectServiceImpl.class);
    }
}
