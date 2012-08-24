package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.event.ActivityAction.Action.SAVE;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * <p>
 * Presenter for modifying an activity.
 * </p>
 * <h3>Events</h3> </p>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ActivityActionEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none
 * </ul>
 * TODO: Replace the popup based version with an inplace editor which is
 * expanded: http://showcase2.jlabanca-testing.appspot.com/#!CwCustomDataGrid
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class EditActivityPresenter extends PresenterWidget<EditActivityPresenter.MyView> implements
        EditAvtivityUiHandlers
{
    public interface MyView extends PopupView, HasUiHandlers<EditAvtivityUiHandlers>
    {
        void setActivity(Activity activity);
    }


    @Inject
    public EditActivityPresenter(final EventBus eventBus, final EditActivityPresenter.MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }


    @Override
    public void onSave(Activity activity)
    {
        ActivityActionEvent.fire(this, new ActivityActionEvent(SAVE, activity));
    }
}
