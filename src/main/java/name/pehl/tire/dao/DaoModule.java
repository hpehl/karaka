package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.CompundNormalizer;
import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.dao.normalize.RemoveNormalizer;
import name.pehl.tire.dao.normalize.ToLowerCaseNormalizer;
import name.pehl.tire.dao.normalize.TrimNormalizer;

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
