package name.pehl.tire.server.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public final class EMF
{
    private static final EntityManagerFactory emfInstance = Persistence
            .createEntityManagerFactory("transactions-optional");


    private EMF()
    {
    }


    public static EntityManagerFactory get()
    {
        return emfInstance;
    }
}
