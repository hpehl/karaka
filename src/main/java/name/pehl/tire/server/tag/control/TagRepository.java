package name.pehl.tire.server.tag.control;

import com.google.inject.Inject;

import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.normalize.TireNormalizer;
import name.pehl.tire.server.repository.NamedEntityRepository;
import name.pehl.tire.server.tag.entity.Tag;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class TagRepository extends NamedEntityRepository<Tag>
{
    @Inject
    public TagRepository(@TireNormalizer Normalizer normalizer)
    {
        super(Tag.class, normalizer);
    }
}
