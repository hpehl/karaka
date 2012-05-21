package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.dispatch.GetYearsAction;
import name.pehl.tire.client.activity.dispatch.GetYearsResult;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.TimeUnit;
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
    public void onSelectYearAndMonth(int year, int month)
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(year)).with(
                "month", String.valueOf(month));
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onSelectYearAndWeek(int year, int week)
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(year)).with(
                "week", String.valueOf(week));
        placeManager.revealPlace(placeRequest);
    }
}
