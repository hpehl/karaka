package name.pehl.karaka.client.project;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.project.ProjectAction.Action;
import name.pehl.karaka.shared.model.Project;

import java.util.List;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none</li>
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 22:35:01 +0100 (Mi, 01. Dez 2010) $ $Revision: 85
 *          $
 */
public class ProjectsPresenter extends Presenter<ProjectsPresenter.MyView, ProjectsPresenter.MyProxy> implements
        ProjectsUiHandlers
{
    @ProxyCodeSplit
    @NameToken(NameTokens.projects)
    public interface MyProxy extends ProxyPlace<ProjectsPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<ProjectsUiHandlers>
    {
        void updateProjects(List<Project> projects);
    }

    final ProjectsCache projectsCache;


    @Inject
    public ProjectsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final ProjectsCache projectsCache)
    {
        super(eventBus, view, proxy);
        this.projectsCache = projectsCache;
        getView().setUiHandlers(this);
    }


    // ---------------------------------------------------- presenter lifecycle

    @Override
    public void prepareFromRequest(final PlaceRequest placeRequest)
    {
        super.prepareFromRequest(placeRequest);
        getView().updateProjects(projectsCache.list());
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_MainContent, this);
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onProjectAction(final Action action, final Project project)
    {
        // TODO Auto-generated method stub
    }
}
