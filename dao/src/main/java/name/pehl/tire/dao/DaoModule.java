package name.pehl.tire.dao;

import name.pehl.tire.dao.search.CompundNormalizer;
import name.pehl.tire.dao.search.Normalizer;
import name.pehl.tire.dao.search.RemoveNormalizer;
import name.pehl.tire.dao.search.ToLowerCaseNormalizer;
import name.pehl.tire.dao.search.TrimNormalizer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
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


    @Provides
    Normalizer provideNormalizer()
    {
        return new CompundNormalizer(new ToLowerCaseNormalizer(), new TrimNormalizer(), new RemoveNormalizer(
                "^!\"§$%&/()=?*+~'#_-:.;,<>|@€"));
    }
}
