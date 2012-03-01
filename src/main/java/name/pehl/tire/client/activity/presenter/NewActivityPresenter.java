package name.pehl.tire.client.activity.presenter;

import java.util.Date;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 21:37:43 +0100 (Fr, 17. Dez 2010) $ $Revision: 164
 *          $
 */
public class NewActivityPresenter extends PresenterWidget<NewActivityPresenter.MyView> implements NewActivityUiHandlers
{
    public interface MyView extends View, HasUiHandlers<NewActivityUiHandlers>
    {
    }

    private Date activityDate;


    @Inject
    public NewActivityPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }


    @Override
    public void onSelectDay(Date date)
    {
        this.activityDate = date;
    }
}
