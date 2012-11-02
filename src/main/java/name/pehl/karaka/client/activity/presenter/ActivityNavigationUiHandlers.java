package name.pehl.karaka.client.activity.presenter;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface ActivityNavigationUiHandlers extends UiHandlers
{
    void onCurrentWeek();


    void onCurrentMonth();


    void onPrevious();


    void onNext();


    void onSelectWeek(int left, int top);


    void onSelectMonth(int left, int top);
}
