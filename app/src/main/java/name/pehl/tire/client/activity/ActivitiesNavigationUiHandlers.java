package name.pehl.tire.client.activity;

import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 169
 *          $
 */
public interface ActivitiesNavigationUiHandlers extends UiHandlers
{
    void onPrev();


    void onCurrent();


    void onNext();


    void changeUnit(TimeUnit unit);
}
