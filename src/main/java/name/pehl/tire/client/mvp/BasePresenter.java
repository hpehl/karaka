package name.pehl.tire.client.mvp;

import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * Base presenter with default method implementations. The following methods are
 * implemented by this class:
 * <ul>
 * <li>{@link #onBind()}
 * <li>{@link #onUnbind()}
 * <li>{@link #refreshDisplay()}
 * <li>{@link #getPlace()}
 * <li>{@link #onPlaceRequest(PlaceRequest)}
 * </ul>
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class BasePresenter<D extends WidgetDisplay> extends WidgetPresenter<D>
{
    public BasePresenter(D display, EventBus eventBus)
    {
        super(display, eventBus);
    }


    /**
     * Empty implementation
     * 
     * @see net.customware.gwt.presenter.client.BasicPresenter#onBind()
     */
    @Override
    protected void onBind()
    {
    }


    /**
     * Empty implementation
     * 
     * @see net.customware.gwt.presenter.client.BasicPresenter#onUnbind()
     */
    @Override
    protected void onUnbind()
    {
    }


    /**
     * Empty implementation
     * 
     * @see net.customware.gwt.presenter.client.Presenter#refreshDisplay()
     */
    @Override
    public void refreshDisplay()
    {
    }


    /**
     * Empty implementation
     * 
     * @return <code>null</code>
     * @see net.customware.gwt.presenter.client.BasicPresenter#getPlace()
     */
    @Override
    public Place getPlace()
    {
        return null;
    }


    /**
     * Empty implementation
     * 
     * @param request
     * @see net.customware.gwt.presenter.client.BasicPresenter#onPlaceRequest(net.customware.gwt.presenter.client.place.PlaceRequest)
     */
    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
    }
}
