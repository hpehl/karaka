package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasOneWidget;
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
    @UiField HasOneWidget newActivityPanel;
    @UiField HasOneWidget activityNavigationPanel;
    @UiField HasOneWidget activityListPanel;


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
    public void setInSlot(Object slot, Widget content)
    {
        if (slot == DashboardPresenter.SLOT_NewActivity)
        {
            UiUtils.setContent(newActivityPanel, content);
        }
        else if (slot == DashboardPresenter.SLOT_ActivityNavigation)
        {
            UiUtils.setContent(activityNavigationPanel, content);
        }
        else if (slot == DashboardPresenter.SLOT_ActivityList)
        {
            UiUtils.setContent(activityListPanel, content);
        }
        else
        {
            super.setInSlot(slot, content);
        }
    }
}
