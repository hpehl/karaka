package name.pehl.karaka.client.activity.presenter;

import static name.pehl.karaka.shared.model.TimeUnit.WEEK;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class SelectWeekPresenter extends SelectTimeUnitPresenter
{
    @Inject
    public SelectWeekPresenter(EventBus eventBus, SelectTimeUnitPresenter.MyView view, DispatchAsync dispatcher,
            PlaceManager placeManager)
    {
        super(eventBus, view, dispatcher, placeManager, WEEK);
    }
}
