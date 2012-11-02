package name.pehl.karaka.server.project.entity;

import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.entity.DescriptiveEntity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Entity
public class Project extends DescriptiveEntity
{
    private static final long serialVersionUID = -8354180510050961764L;

    private Key<Client> client;


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


    public Key<Client> getClient()
    {
        return client;
    }


    public void setClient(Key<Client> client)
    {
        this.client = client;
    }
}
