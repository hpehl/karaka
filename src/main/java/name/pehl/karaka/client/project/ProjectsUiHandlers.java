package name.pehl.karaka.client.project;

import name.pehl.karaka.client.project.ProjectAction.Action;
import name.pehl.karaka.shared.model.Project;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface ProjectsUiHandlers extends UiHandlers
{
    void onProjectAction(Action action, Project project);
}
