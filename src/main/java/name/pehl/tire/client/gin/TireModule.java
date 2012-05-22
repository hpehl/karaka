package name.pehl.tire.client.gin;

import javax.inject.Singleton;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.TirePlaceManager;
import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.about.AboutView;
import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.activity.model.DayReader;
import name.pehl.tire.client.activity.model.WeekReader;
import name.pehl.tire.client.activity.model.YearReader;
import name.pehl.tire.client.activity.model.YearsReader;
import name.pehl.tire.client.activity.presenter.ActivityStateMachine;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekPresenter;
import name.pehl.tire.client.activity.view.ActivitiesTableResources;
import name.pehl.tire.client.activity.view.DashboardView;
import name.pehl.tire.client.activity.view.EditActivityView;
import name.pehl.tire.client.activity.view.QuickChartView;
import name.pehl.tire.client.activity.view.SelectYearAndMonthOrWeekView;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.ApplicationView;
import name.pehl.tire.client.application.MessagePresenter;
import name.pehl.tire.client.application.MessageView;
import name.pehl.tire.client.application.NavigationPresenter;
import name.pehl.tire.client.application.NavigationView;
import name.pehl.tire.client.client.ClientPresenter;
import name.pehl.tire.client.client.ClientView;
import name.pehl.tire.client.cockpit.CockpitPresenter;
import name.pehl.tire.client.cockpit.CockpitView;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.help.HelpView;
import name.pehl.tire.client.project.ProjectPresenter;
import name.pehl.tire.client.project.ProjectReader;
import name.pehl.tire.client.project.ProjectView;
import name.pehl.tire.client.project.ProjectWriter;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.report.ReportView;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.settings.SettingsPresenter;
import name.pehl.tire.client.settings.SettingsReader;
import name.pehl.tire.client.settings.SettingsView;
import name.pehl.tire.client.settings.UserReader;
import name.pehl.tire.client.tag.TagPresenter;
import name.pehl.tire.client.tag.TagReader;
import name.pehl.tire.client.tag.TagView;
import name.pehl.tire.client.tag.TagWriter;
import name.pehl.tire.client.terms.TermsPresenter;
import name.pehl.tire.client.terms.TermsView;

import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 202 $
 */
public class TireModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        // GWTP stuff
        install(new DefaultModule(TirePlaceManager.class));

        // Resources
        bind(I18n.class).in(Singleton.class);
        bind(Resources.class).in(Singleton.class);
        bind(ActivitiesTableResources.class).in(Singleton.class);

        // Rest Action Handlers
        bind(GetYearsHandler.class);
        bind(GetActivitiesHandler.class);

        // JsonReader / Writer
        // Bind them as eager singletons so that the JsonRegistry
        // is setup correctly!
        bind(ActivitiesReader.class).asEagerSingleton();
        bind(ActivityReader.class).asEagerSingleton();
        bind(DayReader.class).asEagerSingleton();
        bind(ProjectReader.class).asEagerSingleton();
        bind(ProjectWriter.class).asEagerSingleton();
        bind(SettingsReader.class).asEagerSingleton();
        bind(TagReader.class).asEagerSingleton();
        bind(TagWriter.class).asEagerSingleton();
        bind(UserReader.class).asEagerSingleton();
        bind(WeekReader.class).asEagerSingleton();
        bind(YearReader.class).asEagerSingleton();
        bind(YearsReader.class).asEagerSingleton();

        // Constants
        bindConstant().annotatedWith(SecurityCookie.class).to("TST");
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.dashboard);

        // Misc
        bind(ActivityStateMachine.class).asEagerSingleton();

        // PresenterWidgets (a-z)
        bindPresenterWidget(CockpitPresenter.class, CockpitPresenter.MyView.class, CockpitView.class);
        bindPresenterWidget(EditActivityPresenter.class, EditActivityPresenter.MyView.class, EditActivityView.class);
        bindPresenterWidget(MessagePresenter.class, MessagePresenter.MyView.class, MessageView.class);
        bindPresenterWidget(NavigationPresenter.class, NavigationPresenter.MyView.class, NavigationView.class);
        bindPresenterWidget(QuickChartPresenter.class, QuickChartPresenter.MyView.class, QuickChartView.class);
        bindPresenterWidget(SelectYearAndMonthOrWeekPresenter.class, SelectYearAndMonthOrWeekPresenter.MyView.class,
                SelectYearAndMonthOrWeekView.class);

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
        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);
        bindPresenter(TagPresenter.class, TagPresenter.MyView.class, TagView.class, TagPresenter.MyProxy.class);
        bindPresenter(TermsPresenter.class, TermsPresenter.MyView.class, TermsView.class, TermsPresenter.MyProxy.class);
    }
}
