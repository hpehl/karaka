package name.pehl.tire.client.project;

import name.pehl.tire.client.model.ProjectDto;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
@GenDispatch(serviceName = "project")
public class GetProject
{
    @In(1)
    Long id;

    @Out(1)
    ProjectDto project;
}
