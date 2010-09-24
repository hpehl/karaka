package name.pehl.tire.client.dashboard;

import java.util.Date;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public class NewActivityPresenter extends PresenterWidget<NewActivityPresenter.MyView> implements NewActivityUiHandlers
{
    public interface MyView extends View, HasUiHandlers<NewActivityUiHandlers>
    {
    }


    @Inject
    public NewActivityPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
        getView().setUiHandlers(this);
    }


    @Override
    public void onSelectDay(Date date)
    {
        // TODO Implement me!
        throw new UnsupportedOperationException("Not implemented");
    }
}
