package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.event.GetYearsAction;
import name.pehl.tire.client.activity.event.GetYearsResult;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;
import name.pehl.tire.shared.model.Years;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SelectYearAndMonthOrWeekPresenter extends PresenterWidget<SelectYearAndMonthOrWeekPresenter.MyView>
{
    public interface MyView extends PopupView
    {
        void updateYears(Years years);
    }

    private final DispatchAsync dispatcher;
    private Years years;
    private TimeUnit unit;
    private YearAndMonthOrWeek currentValue;


    @Inject
    public SelectYearAndMonthOrWeekPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
    }


    @Override
    protected void onReveal()
    {
        super.onReset();
        // TODO Implement some caching to prevent server roundtrip
        dispatcher.execute(new GetYearsAction(), new TireCallback<GetYearsResult>(getEventBus())
        {
            @Override
            public void onSuccess(GetYearsResult result)
            {
                years = result.getYears();
                getView().updateYears(years);
            }
        });
    }


    public void setUnit(TimeUnit unit)
    {
        this.unit = unit;
    }
}
