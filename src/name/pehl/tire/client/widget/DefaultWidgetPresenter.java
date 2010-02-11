package name.pehl.tire.client.widget;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class DefaultWidgetPresenter<D extends WidgetDisplay> extends WidgetPresenter<D>
{
    /**
     * Construct a new instance of this class
     * @param display
     * @param eventBus
     */
    public DefaultWidgetPresenter(D display, EventBus eventBus)
    {
        super(display, eventBus);
    }


    /**
     * @return <code>null</code>
     * @see net.customware.gwt.presenter.client.BasicPresenter#getPlace()
     */
    @Override
    public Place getPlace()
    {
        return null;
    }


    /**
     * Empty
     * 
     * @see net.customware.gwt.presenter.client.BasicPresenter#onBind()
     */
    @Override
    protected void onBind()
    {
    }


    /**
     * Empty
     * 
     * @param request
     * @see net.customware.gwt.presenter.client.BasicPresenter#onPlaceRequest(net.customware.gwt.presenter.client.place.PlaceRequest)
     */
    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
    }


    /**
     * Empty
     * 
     * @see net.customware.gwt.presenter.client.BasicPresenter#onUnbind()
     */
    @Override
    protected void onUnbind()
    {
    }


    /**
     * Empty
     * 
     * @see net.customware.gwt.presenter.client.Presenter#refreshDisplay()
     */
    @Override
    public void refreshDisplay()
    {
    }


    /**
     * Empty
     * 
     * @see net.customware.gwt.presenter.client.Presenter#revealDisplay()
     */
    @Override
    public void revealDisplay()
    {
    }
}
