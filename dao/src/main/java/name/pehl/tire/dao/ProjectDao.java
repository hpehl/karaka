package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.Project;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ProjectDao extends NamedEntityDao<Project>
{
    @Inject
    public ProjectDao(User user, Normalizer normalizer)
    {
        super(Project.class, user, normalizer);
    }
}
