package name.pehl.tire.server.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public final class PMF
{
    private static final PersistenceManagerFactory pmfInstance = JDOHelper
            .getPersistenceManagerFactory("transactions-optional");


    private PMF()
    {
    }


    public static PersistenceManagerFactory get()
    {
        return pmfInstance;
    }
}
