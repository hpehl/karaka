package name.pehl.tire.client.status;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public class StatusPresenter extends PresenterWidget<StatusPresenter.MyView> implements StatusUiHandlers
{
    public interface MyView extends View, HasUiHandlers<StatusUiHandlers>
    {
    }


    @Inject
    public StatusPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }


    @Override
    public void onStartRecording()
    {
    }


    @Override
    public void onStopRecording()
    {
    }
}
