package name.pehl.tire.client.application;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class ControlPresenter extends PresenterWidget<ControlPresenter.MyView> implements ControlUiHandlers
{
    public interface MyView extends View, HasUiHandlers<ControlUiHandlers>
    {
        void initializeRecording(boolean recording);
    }


    @Inject
    public ControlPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
        getView().initializeRecording(false);
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
