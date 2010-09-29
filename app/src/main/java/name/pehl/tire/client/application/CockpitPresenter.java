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
public class CockpitPresenter extends PresenterWidget<CockpitPresenter.MyView> implements CockpitUiHandlers
{
    public interface MyView extends View, HasUiHandlers<CockpitUiHandlers>
    {
        void initializeRecording(boolean recording);
    }


    @Inject
    public CockpitPresenter(final EventBus eventBus, final MyView view)
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
