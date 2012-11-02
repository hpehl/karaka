package name.pehl.karaka.client;

import name.pehl.karaka.client.gin.DefaultPlace;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class TirePlaceManager extends PlaceManagerImpl
{
    private final PlaceRequest defaultPlaceRequest;


    @Inject
    public TirePlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken)
    {
        super(eventBus, tokenFormatter);
        this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
    }


    @Override
    public void revealDefaultPlace()
    {
        revealPlace(defaultPlaceRequest, false);
    }
}
