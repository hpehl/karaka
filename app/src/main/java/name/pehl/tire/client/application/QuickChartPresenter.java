package name.pehl.tire.client.application;

import java.util.Date;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class QuickChartPresenter extends PresenterWidget<QuickChartPresenter.MyView>
{
    public interface MyView extends View
    {
        void update(boolean animate, CalendarWeekData... data);
    }


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }


    @Override
    protected void onReset()
    {
        // Test
        getView().update(true, new CalendarWeekData(0, 420, new Date()), new CalendarWeekData(1, 490, new Date()),
                new CalendarWeekData(2, 560, new Date()), new CalendarWeekData(3, 350, new Date()),
                new CalendarWeekData(4, 395, new Date()));
    }
}
