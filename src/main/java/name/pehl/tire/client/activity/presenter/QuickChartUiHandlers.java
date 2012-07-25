package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.shared.model.Week;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 175
 *          $
 */
public interface QuickChartUiHandlers extends UiHandlers
{
    void onCalendarWeekClicked(Week week);
}
