package name.pehl.tire.client.report;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ReportView extends ViewImpl implements ReportPresenter.MyView
{
    public interface Binder extends UiBinder<Widget, ReportView>
    {
    }

    private final Widget widget;


    @Inject
    public ReportView(final Binder binder)
    {
        widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
