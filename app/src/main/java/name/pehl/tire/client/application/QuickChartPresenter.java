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
        void update(CalendarWeekData cwd, boolean animate);
    }


    @Inject
    public QuickChartPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }


    @Override
    @SuppressWarnings("deprecation")
    protected void onReset()
    {
        // Test
        Date start = new Date(110, 9, 11);
        Date end = new Date(110, 9, 15);
        CalendarWeekData cwd = new CalendarWeekData(start, end, 41, 420, 490, 560, 350, 395);
        getView().update(cwd, true);
    }
}
