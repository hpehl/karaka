package name.pehl.tire.client.activity.view;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.presenter.ActivityListPresenter;
import name.pehl.tire.client.activity.presenter.ActivityListUiHandlers;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.Activities;

public class ActivityListView extends ViewWithUiHandlers<ActivityListUiHandlers> implements
        ActivityListPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, ActivityListView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final I18n i18n;
    final Widget widget;

    @UiField(provided = true) ActivitiesTable activitiesTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public ActivityListView(final Binder binder, final I18n i18n, final Resources resources,
            final TableResources tableResources)
    {
        this.i18n = i18n;
        this.activitiesTable = new ActivitiesTable(resources, tableResources);
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateActivities(final Activities activities)
    {
        activitiesTable.update(activities);
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("activitiesTable")
    public void onActivityAction(final ActivityActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onActivityAction(event.getAction(), event.getActivity());
        }
    }
}
