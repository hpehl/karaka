package name.pehl.tire.client.gin;

import javax.inject.Singleton;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.StartupManager;
import name.pehl.tire.client.TirePlaceManager;
import name.pehl.tire.client.about.AboutPresenter;
import name.pehl.tire.client.about.AboutView;
import name.pehl.tire.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.tire.client.activity.dispatch.FindActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;
import name.pehl.tire.client.activity.dispatch.SaveActivityHandler;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.activity.model.ActivityWriter;
import name.pehl.tire.client.activity.model.DayReader;
import name.pehl.tire.client.activity.model.DurationsReader;
import name.pehl.tire.client.activity.model.TimeReader;
import name.pehl.tire.client.activity.model.TimeWriter;
import name.pehl.tire.client.activity.model.WeekReader;
import name.pehl.tire.client.activity.model.YearReader;
import name.pehl.tire.client.activity.model.YearsReader;
import name.pehl.tire.client.activity.presenter.ActivityController;
import name.pehl.tire.client.activity.presenter.ActivityListPresenter;
import name.pehl.tire.client.activity.presenter.ActivityNavigationPresenter;
import name.pehl.tire.client.activity.presenter.CockpitPresenter;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.activity.presenter.NewActivityPresenter;
import name.pehl.tire.client.activity.presenter.QuickChartPresenter;
import name.pehl.tire.client.activity.presenter.SelectMonthPresenter;
import name.pehl.tire.client.activity.presenter.SelectTimeUnitPresenter;
import name.pehl.tire.client.activity.presenter.SelectWeekPresenter;
import name.pehl.tire.client.activity.view.ActivitiesTableResources;
import name.pehl.tire.client.activity.view.ActivityListView;
import name.pehl.tire.client.activity.view.ActivityNavigationView;
import name.pehl.tire.client.activity.view.CockpitView;
import name.pehl.tire.client.activity.view.DashboardView;
import name.pehl.tire.client.activity.view.EditActivityView;
import name.pehl.tire.client.activity.view.NewActivityView;
import name.pehl.tire.client.activity.view.QuickChartView;
import name.pehl.tire.client.activity.view.SelectTimeUnitView;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.ApplicationView;
import name.pehl.tire.client.application.MessagePresenter;
import name.pehl.tire.client.application.MessageView;
import name.pehl.tire.client.application.NavigationPresenter;
import name.pehl.tire.client.application.NavigationView;
import name.pehl.tire.client.client.ClientPresenter;
import name.pehl.tire.client.client.ClientView;
import name.pehl.tire.client.help.HelpPresenter;
import name.pehl.tire.client.help.HelpView;
import name.pehl.tire.client.model.LinkReader;
import name.pehl.tire.client.project.GetProjectsHandler;
import name.pehl.tire.client.project.ProjectsPresenter;
import name.pehl.tire.client.project.ProjectReader;
import name.pehl.tire.client.project.ProjectsView;
import name.pehl.tire.client.project.ProjectWriter;
import name.pehl.tire.client.project.ProjectsCache;
import name.pehl.tire.client.report.ReportPresenter;
import name.pehl.tire.client.report.ReportView;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.settings.GetSettingsHandler;
import name.pehl.tire.client.settings.SettingsCache;
import name.pehl.tire.client.settings.SettingsPresenter;
import name.pehl.tire.client.settings.SettingsReader;
import name.pehl.tire.client.settings.SettingsView;
import name.pehl.tire.client.settings.UserReader;
import name.pehl.tire.client.tag.GetTagsHandler;
import name.pehl.tire.client.tag.TagPresenter;
import name.pehl.tire.client.tag.TagReader;
import name.pehl.tire.client.tag.TagView;
import name.pehl.tire.client.tag.TagWriter;
import name.pehl.tire.client.tag.TagsCache;
import name.pehl.tire.client.terms.TermsPresenter;
import name.pehl.tire.client.terms.TermsView;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Provides;
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
        bind(DeleteActivityHandler.class);
        bind(FindActivityHandler.class);
        bind(GetActivitiesHandler.class);
        bind(GetMinutesHandler.class);
        bind(GetProjectsHandler.class);
        bind(GetRunningActivityHandler.class);
        bind(GetSettingsHandler.class);
        bind(GetTagsHandler.class);
        bind(GetYearsHandler.class);
        bind(SaveActivityHandler.class);

        // JsonReader / Writer
        // Bind them as eager singletons so that the JsonRegistry
        // is setup correctly!
        bind(ActivitiesReader.class).asEagerSingleton();
        bind(ActivityReader.class).asEagerSingleton();
        bind(ActivityWriter.class).asEagerSingleton();
        bind(DayReader.class).asEagerSingleton();
        bind(DurationsReader.class).asEagerSingleton();
        bind(LinkReader.class).asEagerSingleton();
        bind(ProjectReader.class).asEagerSingleton();
        bind(ProjectWriter.class).asEagerSingleton();
        bind(SettingsReader.class).asEagerSingleton();
        bind(TagReader.class).asEagerSingleton();
        bind(TagWriter.class).asEagerSingleton();
        bind(TimeReader.class).asEagerSingleton();
        bind(TimeWriter.class).asEagerSingleton();
        bind(UserReader.class).asEagerSingleton();
        bind(WeekReader.class).asEagerSingleton();
        bind(YearReader.class).asEagerSingleton();
        bind(YearsReader.class).asEagerSingleton();

        // Constants
        bindConstant().annotatedWith(SecurityCookie.class).to("TST");
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.dashboard);

        // PresenterWidgets (a-z)
        bindPresenterWidget(ActivityListPresenter.class, ActivityListPresenter.MyView.class, ActivityListView.class);
        bindPresenterWidget(ActivityNavigationPresenter.class, ActivityNavigationPresenter.MyView.class,
                ActivityNavigationView.class);
        bindPresenterWidget(NewActivityPresenter.class, NewActivityPresenter.MyView.class, NewActivityView.class);
        bindPresenterWidget(CockpitPresenter.class, CockpitPresenter.MyView.class, CockpitView.class);
        bindPresenterWidget(EditActivityPresenter.class, EditActivityPresenter.MyView.class, EditActivityView.class);
        bindPresenterWidget(MessagePresenter.class, MessagePresenter.MyView.class, MessageView.class);
        bindPresenterWidget(NavigationPresenter.class, NavigationPresenter.MyView.class, NavigationView.class);
        bindPresenterWidget(QuickChartPresenter.class, QuickChartPresenter.MyView.class, QuickChartView.class);

        // PresenterWidgets with shared views (a-z)
        bind(SelectMonthPresenter.class);
        bind(SelectWeekPresenter.class);
        bind(SelectTimeUnitPresenter.MyView.class).to(SelectTimeUnitView.class);

        // Presenters (a-z)
        bindPresenter(AboutPresenter.class, AboutPresenter.MyView.class, AboutView.class, AboutPresenter.MyProxy.class);
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
        bindPresenter(ClientPresenter.class, ClientPresenter.MyView.class, ClientView.class,
                ClientPresenter.MyProxy.class);
        bindPresenter(DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class,
                DashboardPresenter.MyProxy.class);
        bindPresenter(HelpPresenter.class, HelpPresenter.MyView.class, HelpView.class, HelpPresenter.MyProxy.class);
        bindPresenter(ProjectsPresenter.class, ProjectsPresenter.MyView.class, ProjectsView.class,
                ProjectsPresenter.MyProxy.class);
        bindPresenter(ReportPresenter.class, ReportPresenter.MyView.class, ReportView.class,
                ReportPresenter.MyProxy.class);
        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);
        bindPresenter(TagPresenter.class, TagPresenter.MyView.class, TagView.class, TagPresenter.MyProxy.class);
        bindPresenter(TermsPresenter.class, TermsPresenter.MyView.class, TermsView.class, TermsPresenter.MyProxy.class);

        // Application specific
        bind(ActivityController.class).in(Singleton.class);
        bind(ProjectsCache.class).in(Singleton.class);
        bind(SettingsCache.class).in(Singleton.class);
        bind(StartupManager.class).in(Singleton.class);
        bind(TagsCache.class).in(Singleton.class);
    }


    @Provides
    public Scheduler provideScheduler()
    {
        return Scheduler.get();
    }
}
