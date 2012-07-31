package name.pehl.tire.client.activity.presenter;

import java.util.Date;

import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Duration;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface DashboardUiHandlers extends UiHandlers
{
    void onSelectDate(Date date);


    void onFindActivity(Request request, Callback callback);


    void onActivitySelected(Activity activity);


    void onActivityEntered(String name);


    void onProjectSelected(Project project);


    void onProjectEntered(String name);


    void onDurationEntered(Duration duration);


    void onNewActivity();


    void onCurrentWeek();


    void onCurrentMonth();


    void onPrevious();


    void onNext();


    void onSelectWeek(int left, int top);


    void onSelectMonth(int left, int top);


    void onActivityAction(Activity activity, Action action);
}
