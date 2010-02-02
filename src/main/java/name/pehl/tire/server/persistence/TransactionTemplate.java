package name.pehl.tire.server.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public abstract class TransactionTemplate
{
    private static final Logger log = Logger.getLogger(TransactionTemplate.class.getName());


    public TransactionTemplate()
    {
    }


    public final void run()
    {
        final Objectify ofy = ObjectifyService.beginTransaction();
        try
        {
            perform();
            ofy.getTxn().commit();
        }
        catch (Throwable t)
        {
            log.log(Level.SEVERE, t.getMessage(), t);
        }
        finally
        {
            if (ofy.getTxn().isActive())
            {
                ofy.getTxn().rollback();
            }
        }
    }


    protected abstract void perform();
}
