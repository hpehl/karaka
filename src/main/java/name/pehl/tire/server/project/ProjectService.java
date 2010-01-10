package name.pehl.tire.server.project;


import java.util.List;



/**
 * @author $Author:$
 * @version $Revision:$
 */
public interface ProjectService
{
    List<Project> list();
    
    void save(Project project);
}
