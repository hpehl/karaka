package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.shared.model.Activity;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface RecentActivitiesUiHandlers extends UiHandlers
{
    void onCurrentWeek();


    void onCurrentMonth();


    void onPrev();


    void onNext();


    void onEdit(int rowIndex, Activity activity);


    void onCopy(int rowIndex, Activity activity);


    void onGoon(int rowIndex, Activity activity);


    void onDelete(int rowIndex, Activity activity);
}
