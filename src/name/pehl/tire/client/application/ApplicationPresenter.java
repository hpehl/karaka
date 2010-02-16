package name.pehl.tire.client.application;

import name.pehl.tire.client.mvp.BasePresenter;
import net.customware.gwt.presenter.client.EventBus;

import com.google.inject.Inject;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ApplicationPresenter extends BasePresenter<ApplicationDisplay>
{
    @Inject
    public ApplicationPresenter(ApplicationDisplay display, EventBus eventBus)
    {
        super(display, eventBus);
    }
}
