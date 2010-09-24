package name.pehl.tire.client.dashboard;

import java.util.Date;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public interface NewActivityUiHandlers extends UiHandlers
{
    void onSelectDay(Date date);
}
