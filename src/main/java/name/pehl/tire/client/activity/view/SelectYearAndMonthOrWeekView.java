package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.presenter.SelectYearAndMonthOrWeekPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class SelectYearAndMonthOrWeekView extends PopupViewImpl implements SelectYearAndMonthOrWeekPresenter.MyView
{
    public interface Binder extends UiBinder<EscapablePopupPanel, SelectYearAndMonthOrWeekView>
    {
    }

    private final EscapablePopupPanel popupPanel;


    @Inject
    public SelectYearAndMonthOrWeekView(final EventBus eventBus, final Binder binder)
    {
        super(eventBus);
        this.popupPanel = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }
}
