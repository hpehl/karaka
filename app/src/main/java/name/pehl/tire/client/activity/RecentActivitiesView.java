package name.pehl.tire.client.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 142
 *          $
 */
public class RecentActivitiesView extends ViewWithUiHandlers<RecentActivitiesUiHandlers> implements
        RecentActivitiesPresenter.MyView
{
    interface RecentActivitiesUi extends UiBinder<Widget, RecentActivitiesView>
    {
    }

    private static RecentActivitiesUi uiBinder = GWT.create(RecentActivitiesUi.class);

    private final Widget widget;


    public RecentActivitiesView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
