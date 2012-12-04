package name.pehl.karaka.server.tag.control;

import name.pehl.karaka.server.repository.Repository;
import name.pehl.karaka.server.tag.entity.Tag;

import javax.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class TagRepository extends Repository<Tag>
{
    @Inject
    public TagRepository(TagIndexSearch indexer)
    {
        super(Tag.class, indexer);
    }
}
