package name.pehl.tire.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class DaoModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ActivityDao.class).in(Singleton.class);
        bind(ClientDao.class).in(Singleton.class);
        bind(ProjectDao.class).in(Singleton.class);
        bind(TagDao.class).in(Singleton.class);
    }
}
