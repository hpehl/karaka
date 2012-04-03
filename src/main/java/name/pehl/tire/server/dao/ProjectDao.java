package name.pehl.tire.server.dao;

import com.google.inject.Inject;

import name.pehl.tire.server.dao.normalize.Normalizer;
import name.pehl.tire.server.dao.normalize.TireNormalizer;
import name.pehl.tire.server.model.Project;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ProjectDao extends NamedEntityDao<Project>
{
    @Inject
    public ProjectDao(@TireNormalizer Normalizer normalizer)
    {
        super(Project.class, normalizer);
    }
}
