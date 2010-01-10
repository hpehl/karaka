package name.pehl.tire.client;

import name.pehl.tire.client.project.ProjectPresenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

public class AppPresenter
{
    private final ProjectPresenter projectPresenter;


    @Inject
    public AppPresenter(final ProjectPresenter projectPresenter)
    {
        this.projectPresenter = projectPresenter;
    }


    public void go(final HasWidgets container)
    {
        // let's go!
        container.clear();
        container.add(projectPresenter.getDisplay().asWidget());
    }
}
