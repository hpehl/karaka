package name.pehl.tire.dao;

import name.pehl.tire.model.Project;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ProjectDao extends HasUserDao<Project>
{
    @Inject
    public ProjectDao(User user)
    {
        super(Project.class, user);
    }
}
