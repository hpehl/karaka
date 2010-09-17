package name.pehl.tire.client.project;

import name.pehl.tire.client.model.ProjectDto;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenEvent
public class ProjectChanged
{
    @Order(1)
    ProjectDto project;
    
    @Order(2)
    boolean originator;
}
