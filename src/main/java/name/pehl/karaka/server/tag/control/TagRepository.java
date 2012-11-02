package name.pehl.karaka.server.tag.control;

import javax.inject.Inject;

import name.pehl.karaka.server.repository.NamedEntityRepository;
import name.pehl.karaka.server.tag.entity.Tag;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class TagRepository extends NamedEntityRepository<Tag>
{
    @Inject
    public TagRepository(TagIndexSearch indexer)
    {
        super(Tag.class, indexer);
    }
}
