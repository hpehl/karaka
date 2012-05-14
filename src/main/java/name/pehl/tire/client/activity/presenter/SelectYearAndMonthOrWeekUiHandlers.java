package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.shared.model.YearAndMonthOrWeek;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface SelectYearAndMonthOrWeekUiHandlers extends UiHandlers
{
    void onSelectYearAndMonthOrWeek(YearAndMonthOrWeek yearAndMonthOrWeek);
}
