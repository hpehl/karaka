package name.pehl.tire.client.activity.view;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewImpl;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class EditActivityView extends PopupViewImpl implements EditActivityPresenter.MyView
{
    private static Logger logger = Logger.getLogger(EditActivityView.class.getName());

    // @formatter:off
    interface EditActivityUi extends UiBinder<EscapablePopupPanel, EditActivityView> {}
    private static EditActivityUi uiBinder = GWT.create(EditActivityUi.class);
    // @formatter:on

    private final EscapablePopupPanel popupPanel;


    @Inject
    public EditActivityView(EventBus eventBus)
    {
        super(eventBus);
        this.popupPanel = uiBinder.createAndBindUi(this);
        setAutoHideOnNavigationEventEnabled(true);
        setCloseHandler(new PopupViewCloseHandler()
        {
            @Override
            public void onClose()
            {
                logger.fine("Closing edit View");
            }
        });
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }


    @Override
    public void setActivity(Activity activity)
    {

    }
}
