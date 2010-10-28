package name.pehl.tire.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DaoModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ActivityDao.class).in(Singleton.class);
    }
}
