package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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


        void save();
    }


    @Inject
    public EditActivityPresenter(final EventBus eventBus, final EditActivityPresenter.MyView view)
    {
        super(eventBus, view);
    }
}
