package name.pehl.tire.client.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ReportView extends ViewImpl implements ReportPresenter.MyView
{
    interface ReportUi extends UiBinder<Widget, ReportView>
    {
    }

    private static ReportUi uiBinder = GWT.create(ReportUi.class);

    private final Widget widget;


    public ReportView()
    {
        widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
