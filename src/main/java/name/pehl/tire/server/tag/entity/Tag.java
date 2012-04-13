package name.pehl.tire.server.tag.entity;

import name.pehl.tire.server.entity.NamedEntity;

import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Tag extends NamedEntity
{
    private static final long serialVersionUID = -3947128324431639651L;


    Tag()
    {
        this(null);
    }


    public Tag(String name)
    {
        super(name);
    }
}
