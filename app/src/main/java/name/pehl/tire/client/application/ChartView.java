package name.pehl.tire.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class ChartView extends ViewImpl implements ChartPresenter.MyView
{
    interface ChartUi extends UiBinder<Widget, ChartView>
    {
    }

    private static ChartUi uiBinder = GWT.create(ChartUi.class);

    private final Widget widget;


    public ChartView()
    {
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }
}
