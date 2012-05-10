package name.pehl.tire.client.activity.presenter;

import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

public class SelectYearAndMonthOrWeekPresenter extends PresenterWidget<SelectYearAndMonthOrWeekPresenter.MyView>
{

    public interface MyView extends PopupView
    {
        // TODO Put your view methods here
    }

    @Inject
    public SelectYearAndMonthOrWeekPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }
}
