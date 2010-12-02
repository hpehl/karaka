package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.GetWeekAction;
import name.pehl.tire.client.activity.GetWeekResult;
import name.pehl.tire.client.activity.Week;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView>
{
    public interface MyView extends View
    {
        void update(Week week, boolean animate);
    }

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
    }


    @Override
    protected void onReset()
    {
        // TODO Current year and week
        dispatcher.execute(new GetWeekAction(2010, 42), new TireCallback<GetWeekResult>(placeManager)
        {
            @Override
            public void onSuccess(GetWeekResult result)
            {
                Week week = result.getWeek();
                // TODO Get / build week and update view
                // getView().update(week, true);
            }
        });
    }
}
