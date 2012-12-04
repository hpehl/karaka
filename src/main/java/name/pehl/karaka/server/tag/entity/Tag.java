package name.pehl.karaka.server.tag.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import name.pehl.karaka.server.entity.NamedEntity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Cache
@Entity
public class Tag extends NamedEntity
{
    Tag()
    {
        this(null);
    }


    public Tag(String name)
    {
        super(name);
    }
}
