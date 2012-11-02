package name.pehl.karaka.client.activity.presenter;

import java.util.Date;

import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Duration;
import name.pehl.karaka.shared.model.Project;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface NewActivityUiHandlers extends UiHandlers
{
    void onSelectDate(Date date);


    void onFindActivity(Request request, Callback callback);


    void onActivitySelected(Activity activity);


    void onActivityChanged(String name);


    void onProjectSelected(Project project);


    void onProjectChanged(String name);


    void onDurationChanged(Duration duration);


    void onNewActivity();
}
