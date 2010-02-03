package name.pehl.tire.server.project;

import java.util.Iterator;

import name.pehl.tire.shared.model.Project;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public interface ProjectService
{
    Iterator<Project> list();


    void save(Project project);
}
