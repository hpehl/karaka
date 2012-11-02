package name.pehl.karaka.client.client;

import name.pehl.karaka.shared.model.Client;

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
