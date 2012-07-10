package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class SelectMonthPresenter extends SelectTimeUnitPresenter
{
    @Inject
    public SelectMonthPresenter(EventBus eventBus, MyView view, DispatchAsync dispatcher, PlaceManager placeManager)
    {
        super(eventBus, view, dispatcher, placeManager, MONTH);
    }
}
