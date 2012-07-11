package name.pehl.tire.server.tag.control;

import name.pehl.tire.server.repository.NamedEntityRepository;
import name.pehl.tire.server.tag.entity.Tag;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class TagRepository extends NamedEntityRepository<Tag>
{
    public TagRepository()
    {
        super(Tag.class);
    }
}
