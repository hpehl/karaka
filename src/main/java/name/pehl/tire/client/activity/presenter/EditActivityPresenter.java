package name.pehl.tire.client.activity.presenter;

import javax.inject.Inject;

import name.pehl.tire.client.activity.model.Activity;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class EditActivityPresenter extends PresenterWidget<EditActivityPresenter.MyView>
{
    public interface MyView extends PopupView
    {
        void setActivity(Activity activity);
    }


    @Inject
    public EditActivityPresenter(final EventBus eventBus, final EditActivityPresenter.MyView view)
    {
        super(eventBus, view);
    }
}
