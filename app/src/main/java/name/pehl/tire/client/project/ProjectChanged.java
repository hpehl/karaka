package name.pehl.tire.client.project;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-17 15:31:02 +0200 (Fr, 17 Sep 2010) $ $Revision: 69 $
 */
@GenEvent
public class ProjectChanged
{
    @Order(1)
    ProjectDto project;
    
    @Order(2)
    boolean originator;
}
