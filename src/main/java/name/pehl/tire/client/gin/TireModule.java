package name.pehl.tire.client.gin;

import name.pehl.tire.client.activity.ActivityDisplay;
import name.pehl.tire.client.activity.ActivityPresenter;
import name.pehl.tire.client.activity.ActivityView;
import name.pehl.tire.client.application.ApplicationDisplay;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.ApplicationView;
import name.pehl.tire.client.i18n.I18n;
import name.pehl.tire.client.project.ProjectClient;
import name.pehl.tire.client.project.ProjectDisplay;
import name.pehl.tire.client.project.ProjectPresenter;
import name.pehl.tire.client.project.ProjectView;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultEventBus;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TireModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).in(Singleton.class);
        bind(I18n.class).in(Singleton.class);
        bind(ProjectClient.class).in(Singleton.class);

        bindPresenter(ApplicationPresenter.class, ApplicationDisplay.class, ApplicationView.class);
        bindPresenter(ProjectPresenter.class, ProjectDisplay.class, ProjectView.class);
        bindPresenter(ActivityPresenter.class, ActivityDisplay.class, ActivityView.class);
    }
}
