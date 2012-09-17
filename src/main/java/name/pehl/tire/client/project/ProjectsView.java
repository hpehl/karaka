package name.pehl.tire.client.project;

import java.util.List;

import name.pehl.tire.client.cell.ModelsTableResources;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ProjectsView extends ViewWithUiHandlers<ProjectsUiHandlers> implements ProjectsPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, ProjectsView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final I18n i18n;
    final Widget widget;

    @UiField(provided = true) ProjectsTable projectsTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public ProjectsView(final Binder binder, final I18n i18n, final ProjectsTemplates projectTemplates,
            final ProjectsTableResources projectTableResources, final ModelsTableResources commonTableResources)
    {
        this.i18n = i18n;
        this.projectsTable = new ProjectsTable(projectTemplates, projectTableResources, commonTableResources);
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateProjects(final List<Project> projects)
    {
        projectsTable.update(projects);
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("projectsTable")
    public void onProjectAction(final ProjectActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onProjectAction(event.getAction(), event.getProject());
        }
    }
}
