package name.pehl.tire.client.project;

import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

public class ProjectModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        bindPresenter(ProjectPresenter.class, ProjectPresenter.Display.class, ProjectView.class);
    }
}
