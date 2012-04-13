package name.pehl.tire.server.tag.entity;

import javax.xml.bind.annotation.XmlTransient;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.entity.NamedEntity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Tag extends NamedEntity
{
    private static final long serialVersionUID = -3947128324431639651L;

    private Key<Activity> activity;


    Tag()
    {
        this(null);
    }


    public Tag(String name)
    {
        super(name);
    }


    @XmlTransient
    public Key<Activity> getActivity()
    {
        return activity;
    }


    public void setActivity(Key<Activity> activity)
    {
        this.activity = activity;
    }
}
