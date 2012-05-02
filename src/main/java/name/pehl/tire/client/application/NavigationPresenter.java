package name.pehl.tire.client.application;

import name.pehl.tire.client.application.ShowMessageEvent.ShowMessageHandler;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 95
 *          $
 */
public class NavigationPresenter extends PresenterWidget<NavigationPresenter.MyView> implements ShowMessageHandler
{
    public interface MyView extends View
    {
        void highlight(String token);


        void showMessage(Message message);
    }

    private final PlaceManager placeManager;


    @Inject
    public NavigationPresenter(final EventBus eventBus, final MyView view, final PlaceManager placeManager)
    {
        super(eventBus, view);
        this.placeManager = placeManager;
        eventBus.addHandler(ShowMessageEvent.getType(), this);
    }


    /**
     * {@linkplain NavigationPresenter.MyView#highlight(String) Highlights} the
     * current place in the {@linkplain NavigationPresenter.MyView navigation
     * view}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset()
    {
        PlaceRequest request = placeManager.getCurrentPlaceRequest();
        String token = request.getNameToken();
        getView().highlight(token);
    }


    @Override
    public void onShowMessage(ShowMessageEvent event)
    {
        getView().showMessage(event.getMessage());
    }
}
