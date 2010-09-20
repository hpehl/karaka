package name.pehl.tire.client.project;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author$
 * @version $Date$ $Revision: 69
 *          $
 */
@GenEvent
public class ProjectChanged
{
    @Order(1)
    ProjectDto project;

    @Order(2)
    boolean originator;
}
