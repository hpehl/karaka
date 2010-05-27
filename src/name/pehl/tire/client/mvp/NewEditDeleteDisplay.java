package name.pehl.tire.client.mvp;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface NewEditDeleteDisplay
{
    HasClickHandlers getNewClick();


    HasClickHandlers getEditClick();


    HasClickHandlers getDeleteClick();
}
