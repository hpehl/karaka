package name.pehl.karaka.client.project;

import java.util.List;

import name.pehl.karaka.shared.model.Project;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetProjects
{
    @Out(1) List<Project> projects;
}
