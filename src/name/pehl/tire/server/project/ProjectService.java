package name.pehl.tire.server.project;

import java.util.List;

import name.pehl.tire.shared.model.Project;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public interface ProjectService
{
    List<Project> list();


    void save(Project project);
}
