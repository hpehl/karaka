package name.pehl.tire.server.dao;

import name.pehl.tire.server.activity.conotrol.ActivityDao;
import name.pehl.tire.server.normalize.CompundNormalizer;
import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.normalize.RemoveNormalizer;
import name.pehl.tire.server.normalize.ToLowerCaseNormalizer;
import name.pehl.tire.server.normalize.TrimNormalizer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
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
        bind(SearchDao.class).in(Singleton.class);
        bind(TagDao.class).in(Singleton.class);
    }


    @Provides
    Normalizer provideNormalizer()
    {
        return new CompundNormalizer(new ToLowerCaseNormalizer(), new TrimNormalizer(), new RemoveNormalizer(
                "^!\"§$%&/()=?*+~'#_-:.;,<>|@€"));
    }
}
