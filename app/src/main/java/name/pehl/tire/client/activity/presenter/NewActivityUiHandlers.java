package name.pehl.tire.client.activity.presenter;

import java.util.Date;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public interface NewActivityUiHandlers extends UiHandlers
{
    void onSelectDay(Date date);
}
