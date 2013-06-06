package name.pehl.karaka.client.activity.view;

import com.google.gwt.user.client.ui.IsWidget;
import name.pehl.karaka.client.activity.presenter.DashboardPresenter;
import name.pehl.karaka.client.ui.UiUtils;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * Ideas:
 * <ul>
 * <li>Place buttons / clickable labels with the current tags next to the
 * header. Clicking the labels will filter the activities. See
 * http://meteor.com/examples/todos
 * </ul>
 */
public class DashboardView extends ViewImpl implements DashboardPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, DashboardView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final Widget widget;
    @UiField HasWidgets newActivityPanel;
    @UiField HasWidgets activityNavigationPanel;
    @UiField HasWidgets activityListPanel;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardView(final Binder binder)
    {
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void setInSlot(Object slot, IsWidget content)
    {
        if (slot == DashboardPresenter.TYPE_NewActivity)
        {
            UiUtils.setContent(newActivityPanel, content);
        }
        else if (slot == DashboardPresenter.TYPE_ActivityNavigation)
        {
            UiUtils.setContent(activityNavigationPanel, content);
        }
        else if (slot == DashboardPresenter.TYPE_ActivityList)
        {
            UiUtils.setContent(activityListPanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }
}
