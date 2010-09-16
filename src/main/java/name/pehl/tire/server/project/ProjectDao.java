package name.pehl.tire.server.project;

import name.pehl.tire.server.model.Project;
import name.pehl.tire.server.persistence.ObjectifyDao;

import com.googlecode.objectify.ObjectifyService;

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
