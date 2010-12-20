package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 169
 *          $
 */
public interface ActivitiesNavigationUiHandlers extends UiHandlers
{
    void onRelative(int offset);


    void onPrev();


    void onCurrent();


    void onNext();


    void changeUnit(TimeUnit unit);
}
