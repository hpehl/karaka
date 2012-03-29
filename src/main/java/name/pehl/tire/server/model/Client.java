package name.pehl.tire.server.model;

import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Client extends DescriptiveEntity
{
    private static final long serialVersionUID = -3043154607347333944L;


    Client()
    {
        this(null, null);
    }


    public Client(String name)
    {
        this(name, null);
    }


    public Client(String name, String description)
    {
        super(name, description);
    }
}
