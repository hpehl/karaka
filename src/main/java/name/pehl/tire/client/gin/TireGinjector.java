package name.pehl.tire.client.gin;

import name.pehl.tire.client.StartupManager;
import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.client.ClientsPresenter;
import name.pehl.tire.client.dispatch.ClientDispatchModule;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.project.ProjectsPresenter;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.settings.SettingsPresenter;
import name.pehl.tire.client.tag.TagsPresenter;
import name.pehl.tire.client.terms.TermsPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 173 $
 */
// @formatter:off
@GinModules({ClientDispatchModule.class, TireModule.class})
public interface TireGinjector extends Ginjector
{
    // ------------------------------------------------------------- singletons

    EventBus getEventBus();
    PlaceManager getPlaceManager();
    DispatchAsync getDispathcer();
    StartupManager getStartupManager();

    // ------------------------------------------------------- presenters (a-z)

    AsyncProvider<AboutPresenter> getAboutPresenter();
    Provider<ApplicationPresenter> getApplicationPresenter();
    AsyncProvider<ClientsPresenter> getClientsPresenter();
    Provider<DashboardPresenter> getDashboardPresenter();
    AsyncProvider<HelpPresenter> getHelpPresenter();
    AsyncProvider<ProjectsPresenter> getProjectsPresenter();
    AsyncProvider<ReportPresenter> getReportPresenter();
    AsyncProvider<TagsPresenter> getTagsPresenter();
    AsyncProvider<TermsPresenter> getTermsPresenter();
    AsyncProvider<SettingsPresenter> getSettingsPresenter();
}
