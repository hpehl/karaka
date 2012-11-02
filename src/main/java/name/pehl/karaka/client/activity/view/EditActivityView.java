package name.pehl.karaka.client.activity.view;

import java.util.List;
import java.util.logging.Logger;

import name.pehl.karaka.client.activity.presenter.EditActivityPresenter;
import name.pehl.karaka.client.activity.presenter.EditAvtivityUiHandlers;
import name.pehl.karaka.client.model.NamedModelSuggestOracle;
import name.pehl.karaka.client.model.NamedModelSuggestion;
import name.pehl.karaka.client.project.ProjectsCache;
import name.pehl.karaka.client.tag.TagsCache;
import name.pehl.karaka.client.ui.EscapablePopupPanel;
import name.pehl.karaka.client.ui.Html5TextArea;
import name.pehl.karaka.client.ui.Html5TextBox;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Project;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
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
    private Project currentProject;

    @UiField Html5TextBox name;
    @UiField Html5TextArea description;
    @UiField TimeTextBox start;
    @UiField TimeTextBox end;
    @UiField DurationTextBox pause;
    @UiField DurationTextBox duration;
    @UiField(provided = true) @Ignore SuggestBox project;
    @UiField(provided = true) TagsEditorWidget tags;
    @UiField Button cancel;
    @UiField Button save;


    @Inject
    public EditActivityView(final EventBus eventBus, final Binder binder, final Driver driver,
            final ProjectsCache projectsCache, final TagsCache tagsCache)
    {
        super(eventBus);

        NamedModelSuggestOracle<Project> projectOracle = new NamedModelSuggestOracle<Project>(projectsCache);
        Html5TextBox projectTextBox = new Html5TextBox();
        projectTextBox.setPlaceholder("Select or enter a new project");
        this.project = new SuggestBox(projectOracle, projectTextBox);

        this.tags = new TagsEditorWidget(tagsCache);

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
        if (activityToEdit.getProject() != null)
        {
            currentProject = activityToEdit.getProject();
            project.setValue(activityToEdit.getProject().getName());
        }
        else
        {
            currentProject = null;
            project.setValue("");
        }
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
                changedActivity.setProject(currentProject);
                getUiHandlers().onSave(changedActivity);
            }
        }
    }


    @Override
    public void setActivity(Activity activity)
    {
        this.activityToEdit = activity;
    }


    @UiHandler("project")
    @SuppressWarnings("unchecked")
    void onProjectSelected(SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Project> suggestion = (NamedModelSuggestion<Project>) event.getSelectedItem();
        currentProject = suggestion.getModel();
    }


    @UiHandler("project")
    void onProjectChanged(ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            currentProject = new Project(value);
        }
    }
}
