package name.pehl.tire.client.project;

import name.pehl.tire.client.widget.DefaultWidgetPresenter;
import name.pehl.tire.shared.model.Project;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;

public class ProjectPresenter extends DefaultWidgetPresenter<ProjectPresenter.Display>
{
    public interface Display extends WidgetDisplay
    {
        public HasValue<String> getName();


        public HasValue<String> getDescription();


        public HasClickHandlers getNewProject();
    }

    public static final Place PLACE = new Place("project");


    // private final ProjectClient projectClient;

    @Inject
    public ProjectPresenter(final Display display, final EventBus eventBus/*
                                                                           * ,
                                                                           * final
                                                                           * ProjectClient
                                                                           * projectClient
                                                                           */)
    {
        super(display, eventBus);
        // this.projectClient = projectClient;
        bind();
    }


    @Override
    protected void onBind()
    {
        display.getNewProject().addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                doNewProject();
            }
        });
    }


    private void doNewProject()
    {
        Project project = new Project(display.getName().getValue(), display.getDescription().getValue());
        // projectClient.add(project, new RestCallback()
        // {
        // @Override
        // public void onSuccess(Request request, Response response)
        // {
        // Window.alert("Project created!");
        // }
        // });
    }


    /**
     * Returning a place will allow this presenter to automatically trigger when
     * '#Greeting' is passed into the browser URL.
     */
    @Override
    public Place getPlace()
    {
        return PLACE;
    }


    @Override
    protected void onPlaceRequest(final PlaceRequest request)
    {
        // Grab the 'name' from the request and put it into the 'name' field.
        // This allows a tag of '#project;name=Foo' to populate the name
        // field.
        final String name = request.getParameter("name", null);
        if (name != null)
        {
            display.getName().setValue(name);
        }
    }
}
