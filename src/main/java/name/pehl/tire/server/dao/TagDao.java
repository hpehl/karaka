package name.pehl.tire.server.dao;

import javax.inject.Inject;

import name.pehl.tire.server.dao.normalize.Normalizer;
import name.pehl.tire.server.dao.normalize.TireNormalizer;
import name.pehl.tire.server.model.Tag;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class TagDao extends NamedEntityDao<Tag>
{
    @Inject
    public TagDao(@TireNormalizer Normalizer normalizer)
    {
        super(Tag.class, normalizer);
    }
}
