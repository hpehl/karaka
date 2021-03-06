package name.pehl.karaka.server.project.control;

import name.pehl.karaka.server.project.entity.Project;
import name.pehl.karaka.server.repository.Repository;

import javax.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ProjectRepository extends Repository<Project>
{
    @Inject
    public ProjectRepository(ProjectIndexSearch indexer)
    {
        super(Project.class, indexer);
    }
}
