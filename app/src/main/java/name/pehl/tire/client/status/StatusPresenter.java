package name.pehl.tire.client.status;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class StatusPresenter extends PresenterWidget<StatusPresenter.MyView>
{
    public interface MyView extends View
    {
    }


    @Inject
    public StatusPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }
}
