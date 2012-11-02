package name.pehl.karaka.client.activity.presenter;

import name.pehl.karaka.client.activity.event.ActivityAction.Action;
import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface ActivityListUiHandlers extends UiHandlers
{
    void onActivityAction(Action action, Activity activity);
}
