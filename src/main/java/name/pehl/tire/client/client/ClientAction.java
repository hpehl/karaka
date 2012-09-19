package name.pehl.tire.client.client;

import name.pehl.tire.shared.model.Client;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ClientAction
{
    public static enum Action
    {
        DETAILS,
        SAVE,
        DELETE
    }

    @Order(1) Action action;
    @Order(2) Client client;
}
