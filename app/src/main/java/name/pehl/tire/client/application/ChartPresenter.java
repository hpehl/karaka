package name.pehl.tire.client.application;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class ChartPresenter extends PresenterWidget<ChartPresenter.MyView>
{
    public interface MyView extends View
    {
    }


    @Inject
    public ChartPresenter(final EventBus eventBus, final MyView view)
    {
        super(eventBus, view);
    }
}
