package name.pehl.tire.client.activity;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 169
 *          $
 */
public interface RecentActivitiesUiHandlers extends UiHandlers
{
    void onPrev();


    void onCurrent();


    void onNext();
}
