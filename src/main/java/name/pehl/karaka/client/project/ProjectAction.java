package name.pehl.karaka.client.project;

import name.pehl.karaka.shared.model.Project;

import com.gwtplatform.dispatch.annotation.GenEvent;
import com.gwtplatform.dispatch.annotation.Order;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@GenEvent
public class ProjectAction
{
    public static enum Action
    {
        DETAILS,
        SAVE,
        DELETE
    }

    @Order(1) Action action;
    @Order(2) Project project;
}
