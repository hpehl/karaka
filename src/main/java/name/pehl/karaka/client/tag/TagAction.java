package name.pehl.karaka.client.tag;

import name.pehl.karaka.shared.model.Tag;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class TagAction
{
    public static enum Action
    {
        DETAILS,
        SAVE,
        DELETE
    }

    @Order(1) Action action;
    @Order(2) Tag tag;
}
