package name.pehl.tire.server.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public abstract class TransactionTemplate
{
    private static final Log log = LogFactory.getLog(TransactionTemplate.class);

    final private PersistenceManager persistenceManager;


    public TransactionTemplate(PersistenceManager persistenceManager)
    {
        super();
        this.persistenceManager = persistenceManager;
    }


    public final void run()
    {
        Transaction tx = persistenceManager.currentTransaction();
        try
        {
            tx.begin();
            perform();
            tx.commit();
        }
        catch (Throwable t)
        {
            log.error(t.getMessage(), t);
            tx.rollback();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
    }


    protected abstract void perform();
}
