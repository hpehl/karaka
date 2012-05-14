package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.GetYearsAction;
import name.pehl.tire.client.activity.event.GetYearsResult;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;
import name.pehl.tire.shared.model.Years;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class SelectYearAndMonthOrWeekPresenter extends PresenterWidget<SelectYearAndMonthOrWeekPresenter.MyView>
        implements SelectYearAndMonthOrWeekUiHandlers
{
    public interface MyView extends PopupView, HasUiHandlers<SelectYearAndMonthOrWeekUiHandlers>
    {
        void setUnit(TimeUnit unit);


        void updateYears(Years years);
    }

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private Years years;


    @Inject
    public SelectYearAndMonthOrWeekPresenter(final EventBus eventBus, final MyView view,
            final DispatchAsync dispatcher, final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
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
        getView().setUnit(unit);
    }


    @Override
    public void onSelectYearAndMonthOrWeek(YearAndMonthOrWeek yearAndMonthOrWeek)
    {
        PlaceRequest placeRequest = null;
        if (yearAndMonthOrWeek != null)
        {
            if (yearAndMonthOrWeek.getUnit() == MONTH)
            {
                placeRequest = new PlaceRequest(NameTokens.dashboard).with("year",
                        String.valueOf(yearAndMonthOrWeek.getYear())).with("month",
                        String.valueOf(yearAndMonthOrWeek.getMonthOrWeek()));
            }
            else if (yearAndMonthOrWeek.getUnit() == WEEK)
            {
                placeRequest = new PlaceRequest(NameTokens.dashboard).with("year",
                        String.valueOf(yearAndMonthOrWeek.getYear())).with("week",
                        String.valueOf(yearAndMonthOrWeek.getMonthOrWeek()));
            }
        }
        if (placeRequest != null)
        {
            placeManager.revealPlace(placeRequest);
        }
    }
}
