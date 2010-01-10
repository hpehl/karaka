package name.pehl.tire.server.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public abstract class TransactionTemplate
{
    private static final Log log = LogFactory.getLog(TransactionTemplate.class);

    final private EntityManager entityManager;


    public TransactionTemplate(EntityManager entityManager)
    {
        super();
        this.entityManager = entityManager;
    }


    public final void run()
    {
        EntityTransaction tx = entityManager.getTransaction();
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
