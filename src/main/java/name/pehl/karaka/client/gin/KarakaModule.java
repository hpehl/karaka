package name.pehl.karaka.client.gin;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Provides;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import name.pehl.karaka.client.KarakaPlaceManager;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.StartupManager;
import name.pehl.karaka.client.about.AboutPresenter;
import name.pehl.karaka.client.about.AboutView;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.karaka.client.activity.dispatch.FindActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.karaka.client.activity.dispatch.GetMinutesHandler;
import name.pehl.karaka.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetYearsHandler;
import name.pehl.karaka.client.activity.dispatch.SaveActivityHandler;
import name.pehl.karaka.client.activity.model.ActivitiesReader;
import name.pehl.karaka.client.activity.model.ActivityReader;
import name.pehl.karaka.client.activity.model.ActivityWriter;
import name.pehl.karaka.client.activity.model.DayReader;
import name.pehl.karaka.client.activity.model.DurationsReader;
import name.pehl.karaka.client.activity.model.TimeReader;
import name.pehl.karaka.client.activity.model.TimeWriter;
import name.pehl.karaka.client.activity.model.WeekReader;
import name.pehl.karaka.client.activity.model.YearReader;
import name.pehl.karaka.client.activity.model.YearsReader;
import name.pehl.karaka.client.activity.presenter.ActivityController;
import name.pehl.karaka.client.activity.presenter.ActivityListPresenter;
import name.pehl.karaka.client.activity.presenter.ActivityNavigationPresenter;
import name.pehl.karaka.client.activity.presenter.CockpitPresenter;
import name.pehl.karaka.client.activity.presenter.DashboardPresenter;
import name.pehl.karaka.client.activity.presenter.EditActivityPresenter;
import name.pehl.karaka.client.activity.presenter.NewActivityPresenter;
import name.pehl.karaka.client.activity.presenter.QuickChartPresenter;
import name.pehl.karaka.client.activity.presenter.SelectMonthPresenter;
import name.pehl.karaka.client.activity.presenter.SelectTimeUnitPresenter;
import name.pehl.karaka.client.activity.presenter.SelectWeekPresenter;
import name.pehl.karaka.client.activity.view.ActivityListView;
import name.pehl.karaka.client.activity.view.ActivityNavigationView;
import name.pehl.karaka.client.activity.view.CockpitView;
import name.pehl.karaka.client.activity.view.DashboardView;
import name.pehl.karaka.client.activity.view.EditActivityView;
import name.pehl.karaka.client.activity.view.NewActivityView;
import name.pehl.karaka.client.activity.view.QuickChartView;
import name.pehl.karaka.client.activity.view.SelectTimeUnitView;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.application.ApplicationView;
import name.pehl.karaka.client.application.MessagePresenter;
import name.pehl.karaka.client.application.MessageView;
import name.pehl.karaka.client.application.NavigationPresenter;
import name.pehl.karaka.client.application.NavigationView;
import name.pehl.karaka.client.client.ClientsCache;
import name.pehl.karaka.client.client.ClientsPresenter;
import name.pehl.karaka.client.client.ClientsView;
import name.pehl.karaka.client.client.GetClientsHandler;
import name.pehl.karaka.client.help.HelpPresenter;
import name.pehl.karaka.client.help.HelpView;
import name.pehl.karaka.client.model.LinkReader;
import name.pehl.karaka.client.project.GetProjectsHandler;
import name.pehl.karaka.client.project.ProjectReader;
import name.pehl.karaka.client.project.ProjectWriter;
import name.pehl.karaka.client.project.ProjectsCache;
import name.pehl.karaka.client.project.ProjectsPresenter;
import name.pehl.karaka.client.project.ProjectsView;
import name.pehl.karaka.client.report.ReportPresenter;
import name.pehl.karaka.client.report.ReportView;
import name.pehl.karaka.client.resources.I18n;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.client.settings.GetSettingsHandler;
import name.pehl.karaka.client.settings.SettingsCache;
import name.pehl.karaka.client.settings.SettingsPresenter;
import name.pehl.karaka.client.settings.SettingsReader;
import name.pehl.karaka.client.settings.SettingsView;
import name.pehl.karaka.client.settings.UserReader;
import name.pehl.karaka.client.tag.GetTagsHandler;
import name.pehl.karaka.client.tag.TagReader;
import name.pehl.karaka.client.tag.TagWriter;
import name.pehl.karaka.client.tag.TagsCache;
import name.pehl.karaka.client.tag.TagsPresenter;
import name.pehl.karaka.client.tag.TagsView;
import name.pehl.karaka.client.terms.TermsPresenter;
import name.pehl.karaka.client.terms.TermsView;

import javax.inject.Singleton;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 202 $
 */
public class KarakaModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        // GWTP stuff
        install(new DefaultModule(KarakaPlaceManager.class));

        // Resources
        bind(I18n.class).in(Singleton.class);
        bind(Resources.class).in(Singleton.class);
        bind(TableResources.class).in(Singleton.class);

        // Rest Action Handlers
        bind(DeleteActivityHandler.class);
        bind(FindActivityHandler.class);
        bind(GetActivitiesHandler.class);
        bind(GetClientsHandler.class);
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
        bindConstant().annotatedWith(SecurityCookie.class).to("KST");
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
        bindPresenter(ClientsPresenter.class, ClientsPresenter.MyView.class, ClientsView.class,
                ClientsPresenter.MyProxy.class);
        bindPresenter(DashboardPresenter.class, DashboardPresenter.MyView.class, DashboardView.class,
                DashboardPresenter.MyProxy.class);
        bindPresenter(HelpPresenter.class, HelpPresenter.MyView.class, HelpView.class, HelpPresenter.MyProxy.class);
        bindPresenter(ProjectsPresenter.class, ProjectsPresenter.MyView.class, ProjectsView.class,
                ProjectsPresenter.MyProxy.class);
        bindPresenter(ReportPresenter.class, ReportPresenter.MyView.class, ReportView.class,
                ReportPresenter.MyProxy.class);
        bindPresenter(SettingsPresenter.class, SettingsPresenter.MyView.class, SettingsView.class,
                SettingsPresenter.MyProxy.class);
        bindPresenter(TagsPresenter.class, TagsPresenter.MyView.class, TagsView.class, TagsPresenter.MyProxy.class);
        bindPresenter(TermsPresenter.class, TermsPresenter.MyView.class, TermsView.class, TermsPresenter.MyProxy.class);

        // Application specific
        bind(ActivityController.class).in(Singleton.class);
        bind(ClientsCache.class).in(Singleton.class);
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
