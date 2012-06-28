package name.pehl.tire.client.activity.view;

import java.util.List;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.client.ui.Html5TextArea;
import name.pehl.tire.client.ui.Html5TextBox;
import name.pehl.tire.client.ui.TimeTextBox;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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

    @UiField Html5TextBox name;
    @UiField Html5TextArea description;
    @UiField TimeTextBox start;
    @UiField TimeTextBox end;
    @UiField @Ignore Html5TextBox pause;
    @UiField @Ignore Html5TextBox duration;
    @UiField @Ignore Html5TextBox tags;
    @UiField @Ignore Html5TextBox project;


    @Inject
    public EditActivityView(final EventBus eventBus, final Binder binder, final Driver driver)
    {
        super(eventBus);
        this.popupPanel = binder.createAndBindUi(this);
        this.popupPanel.setWidth("600px");
        this.driver = driver;
        setAutoHideOnNavigationEventEnabled(true);
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


    @Override
    public void save()
    {
        Activity changedActivity = driver.flush();
        if (driver.hasErrors())
        {
            List<EditorError> errors = driver.getErrors();
            logger.warning("There are errors: " + errors);
        }
        else if (driver.isDirty())
        {
            // TODO Save changes to the activity
        }
    }
}
