package name.pehl.tire.client.gin;

import name.pehl.tire.client.TirePresenter;
import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.client.ClientPresenter;
import name.pehl.tire.client.dashboard.DashboardPresenter;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.project.ProjectPresenter;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.tag.TagPresenter;
import name.pehl.tire.client.terms.TermsPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

/**
 * @author $LastChangedBy$
 * @version $LastChangedRevision$
 */
@GinModules(TireModule.class)
public interface TireGinjector extends Ginjector
{
    // ------------------------------------------------------------- singletons

    // @formatter:off
    EventBus getEventBus();
    PlaceManager getPlaceManager();
    ProxyFailureHandler getProxyFailureHandler();
    // @formatter:on

    // ------------------------------------------------------- presenters (a-z)

    // @formatter:off
    AsyncProvider<AboutPresenter> getAboutPresenter();
    AsyncProvider<ClientPresenter> getClientPresenter();
    Provider<DashboardPresenter> getDashboardPresenter();
    AsyncProvider<HelpPresenter> getHelpPresenter();
    AsyncProvider<ProjectPresenter> getProjectPresenter();
    AsyncProvider<ReportPresenter> getReportPresenter();
    AsyncProvider<TagPresenter> getTagPresenter();
    AsyncProvider<TermsPresenter> getTermsPresenter();
    Provider<TirePresenter> getTirePresenter();
    // @formatter:on
}
