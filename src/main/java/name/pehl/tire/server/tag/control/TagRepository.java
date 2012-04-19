package name.pehl.tire.server.tag.control;

import javax.enterprise.context.ApplicationScoped;

import name.pehl.tire.server.normalizer.Normalizer;
import name.pehl.tire.server.normalizer.TireNormalizer;
import name.pehl.tire.server.repository.NamedEntityRepository;
import name.pehl.tire.server.tag.entity.Tag;

import com.google.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
@ApplicationScoped
public class TagRepository extends NamedEntityRepository<Tag>
{
    @Inject
    public TagRepository(@TireNormalizer Normalizer normalizer)
    {
        super(Tag.class, normalizer);
    }
}
