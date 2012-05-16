package name.pehl.tire.client.activity.presenter;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface SelectYearAndMonthOrWeekUiHandlers extends UiHandlers
{
    void onSelectYearAndMonth(int year, int month);


    void onSelectYearAndWeek(int year, int week);
}
