package name.pehl.tire.server.project;

import com.googlecode.objectify.ObjectifyService;

import name.pehl.tire.server.persistence.ObjectifyDao;
import name.pehl.tire.shared.project.Project;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ProjectDao extends ObjectifyDao<Project>
{
    static
    {
        ObjectifyService.register(Project.class);
    }


    protected ProjectDao()
    {
        super(Project.class);
    }
}
