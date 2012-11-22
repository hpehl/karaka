package name.pehl.karaka.client.gin;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import name.pehl.karaka.client.about.AboutPresenter;
import name.pehl.karaka.client.activity.presenter.DashboardPresenter;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.bootstrap.BootstrapProcess;
import name.pehl.karaka.client.client.ClientsPresenter;
import name.pehl.karaka.client.dispatch.ClientDispatchModule;
import name.pehl.karaka.client.help.HelpPresenter;
import name.pehl.karaka.client.logging.Logger;
import name.pehl.karaka.client.project.ProjectsPresenter;
import name.pehl.karaka.client.report.ReportPresenter;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.settings.SettingsPresenter;
import name.pehl.karaka.client.tag.TagsPresenter;
import name.pehl.karaka.client.terms.TermsPresenter;

@GinModules({ClientDispatchModule.class, KarakaModule.class})
public interface KarakaGinjector extends Ginjector
{
    // ------------------------------------------------------------- singletons

    BootstrapProcess getBootstrapProcess();
    DispatchAsync getDispathcer();
    EventBus getEventBus();
    Logger getLogger();
    PlaceManager getPlaceManager();
    Resources getResources();


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
