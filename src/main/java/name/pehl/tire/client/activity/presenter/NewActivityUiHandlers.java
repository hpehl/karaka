package name.pehl.tire.client.activity.presenter;

import java.util.Date;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17. Dez 2010) $ $Revision: 192
 *          $
 */
public interface NewActivityUiHandlers extends UiHandlers
{
    void onSelectDay(Date date);
}
