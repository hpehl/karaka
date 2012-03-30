package name.pehl.tire.client.cockpit;

import com.google.gwt.event.shared.EventBus;
import javax.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 175 $
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
