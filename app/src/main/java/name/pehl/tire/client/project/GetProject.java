package name.pehl.tire.client.project;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-17 15:31:02 +0200 (Fr, 17 Sep 2010) $ $Revision: 69 $
 */
@GenDispatch(serviceName = "project")
public class GetProject
{
    @In(1)
    Long id;

    @Out(1)
    ProjectDto project;
}
