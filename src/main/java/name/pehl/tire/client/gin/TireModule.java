package name.pehl.tire.client.gin;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.TirePlaceManager;
import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.about.AboutView;
import name.pehl.tire.client.activity.event.GetActivitiesHandler;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.activity.model.ActivityWriter;
import name.pehl.tire.client.activity.model.DayReader;
import name.pehl.tire.client.activity.model.WeekReader;
import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.activity.presenter.NewActivityPresenter;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.activity.view.ActivitiesTableResources;
import name.pehl.tire.client.activity.view.EditActivityView;
import name.pehl.tire.client.activity.view.NewActivityView;
import name.pehl.tire.client.activity.view.QuickChartView;
import name.pehl.tire.client.activity.view.RecentActivitiesView;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.ApplicationView;
import name.pehl.tire.client.client.ClientPresenter;
import name.pehl.tire.client.client.ClientView;
import name.pehl.tire.client.cockpit.CockpitPresenter;
import name.pehl.tire.client.cockpit.CockpitView;
import name.pehl.tire.client.dashboard.DashboardPresenter;
import name.pehl.tire.client.dashboard.DashboardView;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.help.HelpView;
import name.pehl.tire.client.navigation.NavigationPresenter;
import name.pehl.tire.client.navigation.NavigationView;
import name.pehl.tire.client.project.ProjectPresenter;
import name.pehl.tire.client.project.ProjectReader;
import name.pehl.tire.client.project.ProjectView;
import name.pehl.tire.client.project.ProjectWriter;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.report.ReportView;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.tag.TagPresenter;
import name.pehl.tire.client.tag.TagReader;
import name.pehl.tire.client.tag.TagView;
import name.pehl.tire.client.tag.TagWriter;
import name.pehl.tire.client.terms.TermsPresenter;
import name.pehl.tire.client.terms.TermsView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 202 $
 */
public class TireModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        // Singletons
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).to(TirePlaceManager.class).in(Singleton.class);
        bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        bind(RootPresenter.class).asEagerSingleton();

        // Resources
        bind(I18n.class).in(Singleton.class);
        bind(Resources.class).in(Singleton.class);
        bind(ActivitiesTableResources.class).in(Singleton.class);

        // Rest Action Handlers
        bind(GetActivitiesHandler.class);

        // JsonReader / Writer
        // Bind them as eager singletons so that the JsonRegistry
        // is setup correctly!
        bind(ActivitiesReader.class).asEagerSingleton();
        bind(WeekReader.class).asEagerSingleton();
        bind(DayReader.class).asEagerSingleton();
        bind(ActivityReader.class).asEagerSingleton();
        bind(ActivityWriter.class).asEagerSingleton();
        bind(ProjectReader.class).asEagerSingleton();
        bind(ProjectWriter.class).asEagerSingleton();
        bind(TagReader.class).asEagerSingleton();
        bind(TagWriter.class).asEagerSingleton();

        // Constants
        bindConstant().annotatedWith(SecurityCookie.class).to("TST");
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.dashboard);

        // PresenterWidgets (a-z)
        bindPresenterWidget(CockpitPresenter.class, CockpitPresenter.MyView.class, CockpitView.class);
        bindPresenterWidget(EditActivityPresenter.class, EditActivityPresenter.MyView.class, EditActivityView.class);
        bindPresenterWidget(NavigationPresenter.class, NavigationPresenter.MyView.class, NavigationView.class);
        bindPresenterWidget(NewActivityPresenter.class, NewActivityPresenter.MyView.class, NewActivityView.class);
        bindPresenterWidget(QuickChartPresenter.class, QuickChartPresenter.MyView.class, QuickChartView.class);
        bindPresenterWidget(RecentActivitiesPresenter.class, RecentActivitiesPresenter.MyView.class,
                RecentActivitiesView.class);

        // Presenters (a-z)
        bindPresenter(AboutPresenter.class, AboutPresenter.MyView.class, AboutView.class, AboutPresenter.MyProxy.class);
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
        bindPresenter(ClientPresenter.class, ClientPresenter.MyView.class, ClientView.class,
                ClientPresenter.MyProxy.class);
        bindPresenter(DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class,
                DashboardPresenter.MyProxy.class);
        bindPresenter(HelpPresenter.class, HelpPresenter.MyView.class, HelpView.class, HelpPresenter.MyProxy.class);
        bindPresenter(ProjectPresenter.class, ProjectPresenter.MyView.class, ProjectView.class,
                ProjectPresenter.MyProxy.class);
        bindPresenter(ReportPresenter.class, ReportPresenter.MyView.class, ReportView.class,
                ReportPresenter.MyProxy.class);
        bindPresenter(TagPresenter.class, TagPresenter.MyView.class, TagView.class, TagPresenter.MyProxy.class);
        bindPresenter(TermsPresenter.class, TermsPresenter.MyView.class, TermsView.class, TermsPresenter.MyProxy.class);
    }
}
