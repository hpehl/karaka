package name.pehl.tire.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class TirePlaceManager extends PlaceManagerImpl
{
    @Inject
    public TirePlaceManager(EventBus eventBus, TokenFormatter tokenFormatter)
    {
        super(eventBus, tokenFormatter);
    }


    @Override
    public void revealDefaultPlace()
    {
        revealPlace(new PlaceRequest(TirePresenter.nameToken));
    }
}
