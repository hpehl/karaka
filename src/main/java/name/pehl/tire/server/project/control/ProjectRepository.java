package name.pehl.tire.server.project.control;

import javax.inject.Inject;

import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.repository.NamedEntityRepository;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ProjectRepository extends NamedEntityRepository<Project>
{
    @Inject
    public ProjectRepository(ProjectIndexSearch indexer)
    {
        super(Project.class, indexer);
    }
}
