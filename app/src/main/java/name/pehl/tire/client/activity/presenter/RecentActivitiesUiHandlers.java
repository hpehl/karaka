package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 169
 *          $
 */
public interface RecentActivitiesUiHandlers extends UiHandlers
{
    void onRelative(int offset);


    void onPrev();


    void onCurrent();


    void onNext();


    void changeUnit(TimeUnit unit);


    void onEdit(int rowIndex, Activity activity);


    void onCopy(int rowIndex, Activity activity);


    void onGoon(int rowIndex, Activity activity);


    void onDelete(int rowIndex, Activity activity);
}
