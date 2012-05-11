package name.pehl.tire.client.gin;

import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.client.ClientPresenter;
import name.pehl.tire.client.dispatch.ClientDispatchModule;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.project.ProjectPresenter;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.tag.TagPresenter;
import name.pehl.tire.client.terms.TermsPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
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

    // ------------------------------------------------------- presenters (a-z)

    AsyncProvider<AboutPresenter> getAboutPresenter();
    Provider<ApplicationPresenter> getApplicationPresenter();
    AsyncProvider<ClientPresenter> getClientPresenter();
    Provider<DashboardPresenter> getDashboardPresenter();
    AsyncProvider<HelpPresenter> getHelpPresenter();
    AsyncProvider<ProjectPresenter> getProjectPresenter();
    AsyncProvider<ReportPresenter> getReportPresenter();
    AsyncProvider<TagPresenter> getTagPresenter();
    AsyncProvider<TermsPresenter> getTermsPresenter();
}
