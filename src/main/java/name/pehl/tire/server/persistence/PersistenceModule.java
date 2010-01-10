package name.pehl.tire.server.persistence;

import javax.persistence.EntityManager;

import name.pehl.tire.server.project.ProjectService;
import name.pehl.tire.server.project.ProjectServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class PersistenceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ProjectService.class).to(ProjectServiceImpl.class);
    }


    @Provides
    public EntityManager providesEntityManager()
    {
        return EMF.get().createEntityManager();
    }
}
