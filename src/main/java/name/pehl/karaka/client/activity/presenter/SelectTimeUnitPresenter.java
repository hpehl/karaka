package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.client.NameTokens.dashboard;
import name.pehl.karaka.client.activity.dispatch.GetYearsAction;
import name.pehl.karaka.client.activity.dispatch.GetYearsResult;
import name.pehl.karaka.client.dispatch.TireCallback;
import name.pehl.karaka.shared.model.TimeUnit;
import name.pehl.karaka.shared.model.Years;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public abstract class SelectTimeUnitPresenter extends PresenterWidget<SelectTimeUnitPresenter.MyView> implements
        SelectTimeUnitUiHandlers
{
    public interface MyView extends PopupView, HasUiHandlers<SelectTimeUnitUiHandlers>
    {
        void updateYears(Years years);


        void setUnit(TimeUnit unit);
    }

    final DispatchAsync dispatcher;
    final PlaceManager placeManager;
    Years years;


    protected SelectTimeUnitPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager, final TimeUnit unit)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
        getView().setUnit(unit);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
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


    @Override
    public void onSelectYearAndMonth(int year, int month)
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(year)).with("month",
                String.valueOf(month));
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onSelectYearAndWeek(int year, int week)
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(year)).with("week",
                String.valueOf(week));
        placeManager.revealPlace(placeRequest);
    }
}
