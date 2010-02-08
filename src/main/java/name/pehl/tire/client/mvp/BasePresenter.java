package name.pehl.tire.client.mvp;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * Base presenter with default implementation for some abstract methods from
 * {@link WidgetPresenter}:
 * <ul>
 * <li>{@link #onBind()}
 * <li>{@link #onUnbind()}
 * <li>{@link #refreshDisplay()}
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
     * @param request
     * @see net.customware.gwt.presenter.client.BasicPresenter#onPlaceRequest(net.customware.gwt.presenter.client.place.PlaceRequest)
     */
    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
    }
}
