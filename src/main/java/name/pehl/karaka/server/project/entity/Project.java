package name.pehl.karaka.server.project.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.entity.DescriptiveEntity;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Cache
@Entity
public class Project extends DescriptiveEntity
{
    @Load private Ref<Client> client;


    Project()
    {
        this(null, null);
    }


    public Project(String name)
    {
        this(name, null);
    }


    public Project(String name, String description)
    {
        super(name, description);
    }


    public Ref<Client> getClient()
    {
        return client;
    }


    public void setClient(Ref<Client> client)
    {
        this.client = client;
    }
}
