package name.pehl.tire.client.activity.presenter;

import java.util.Date;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface DashboardUiHandlers extends UiHandlers
{
    void onSelectDate(Date date);


    void onCurrentWeek();


    void onCurrentMonth();


    void onPrevious();


    void onNext();


    void onSelectWeek(int left, int top);


    void onSelectMonth(int left, int top);


    void onEdit(int rowIndex, Activity activity);


    void onCopy(int rowIndex, Activity activity);


    void onStartStop(int rowIndex, Activity activity);


    void onDelete(int rowIndex, Activity activity);
}
