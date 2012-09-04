package name.pehl.tire.client.activity.view;

import java.util.List;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.presenter.EditActivityPresenter;
import name.pehl.tire.client.activity.presenter.EditAvtivityUiHandlers;
import name.pehl.tire.client.model.NamedModelSuggestOracle;
import name.pehl.tire.client.project.ProjectsCache;
import name.pehl.tire.client.ui.EscapablePopupPanel;
import name.pehl.tire.client.ui.Html5TextArea;
import name.pehl.tire.client.ui.Html5TextBox;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 * TODO: Replace Tags with custom widgets like in
 * http://meteor.com/examples/todos
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class EditActivityView extends PopupViewWithUiHandlers<EditAvtivityUiHandlers> implements
        EditActivityPresenter.MyView, Editor<Activity>
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
    @UiField DurationTextBox pause;
    @UiField DurationTextBox duration;
    @UiField @Ignore TagsEditorWidget tags;
    @UiField(provided = true) @Ignore SuggestBox project;
    @UiField Button cancel;
    @UiField Button save;


    @Inject
    public EditActivityView(final EventBus eventBus, final Binder binder, final Driver driver,
            final ProjectsCache projectsCache)
    {
        super(eventBus);

        NamedModelSuggestOracle<Project> projectOracle = new NamedModelSuggestOracle<Project>(projectsCache);
        Html5TextBox projectTextBox = new Html5TextBox();
        projectTextBox.setPlaceholder("Select or enter a new project");
        this.project = new SuggestBox(projectOracle, projectTextBox);

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
        tags.setTags(activityToEdit.getTags());
        super.show();
    }


    @Override
    public Widget asWidget()
    {
        return popupPanel;
    }


    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event)
    {
        hide();
    }


    @UiHandler("save")
    void onSaveClicked(ClickEvent event)
    {
        Activity changedActivity = driver.flush();
        if (driver.hasErrors())
        {
            List<EditorError> errors = driver.getErrors();
            logger.warning("There are errors: " + errors);
        }
        else if (driver.isDirty())
        {
            hide();
            if (getUiHandlers() != null)
            {
                getUiHandlers().onSave(changedActivity);
            }
        }
    }


    @Override
    public void setActivity(Activity activity)
    {
        this.activityToEdit = activity;
    }
}
