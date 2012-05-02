package name.pehl.tire.client.activity.view;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.client.ui.PlaceholderTextBox;
import name.pehl.tire.client.ui.TimeTextBox;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewImpl;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class EditActivityView extends PopupViewImpl implements EditActivityPresenter.MyView, Editor<Activity>
{
    public interface Driver extends SimpleBeanEditorDriver<Activity, EditActivityView>
    {
    }

    public interface Binder extends UiBinder<EscapablePopupPanel, EditActivityView>
    {
    }

    private static Logger logger = Logger.getLogger(EditActivityView.class.getName());

    private final EscapablePopupPanel popupPanel;
    private final Driver driver;
    private Activity activityToEdit;
    @UiField PlaceholderTextBox name;
    @UiField PlaceholderTextBox description;
    @UiField TimeTextBox start;
    @UiField TimeTextBox end;


    @Inject
    public EditActivityView(final EventBus eventBus, final Binder binder, final Driver driver)
    {
        super(eventBus);
        this.popupPanel = binder.createAndBindUi(this);
        this.driver = driver;
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
