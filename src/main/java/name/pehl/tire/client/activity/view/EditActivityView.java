package name.pehl.tire.client.activity.view;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.client.ui.PlaceholderTextBox;
import name.pehl.tire.client.ui.TimeTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewImpl;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class EditActivityView extends PopupViewImpl implements EditActivityPresenter.MyView, Editor<Activity>
{
    private static Logger logger = Logger.getLogger(EditActivityView.class.getName());

    // @formatter:off
    interface Driver extends SimpleBeanEditorDriver<Activity, EditActivityView> {}
    private static Driver driver = GWT.create(Driver.class);
    
    interface EditActivityUi extends UiBinder<EscapablePopupPanel, EditActivityView> {}
    private static EditActivityUi uiBinder = GWT.create(EditActivityUi.class);
    
    @UiField PlaceholderTextBox name;
    @UiField PlaceholderTextBox description;
    @UiField TimeTextBox start;
    @UiField TimeTextBox end;
    // @formatter:on

    private Activity activityToEdit;
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
    public void show()
    {
        driver.initialize(this);
        driver.edit(activityToEdit);
        super.show();
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }


    @Override
    public void setActivity(Activity activity)
    {
        this.activityToEdit = activity;
    }
}
