package name.pehl.tire.client.project;

import name.pehl.tire.client.widget.DefaultWidgetPresenter;
import name.pehl.tire.shared.model.Project;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class ProjectPresenter extends DefaultWidgetPresenter<ProjectDisplay>
{
    public static final Place PLACE = new Place("project");

    @SuppressWarnings("unused")
    private final ProjectClient projectClient;


    @Inject
    public ProjectPresenter(final ProjectDisplay display, final EventBus eventBus, final ProjectClient projectClient)
    {
        super(display, eventBus);
        this.projectClient = projectClient;
        bind();
    }


    @Override
    protected void onBind()
    {
        display.getNewClick().addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                doNewProject();
            }
        });
    }


    private void doNewProject()
    {
        @SuppressWarnings("unused") Project project = new Project(display.getName().getValue(), display
                .getDescription().getValue());
    }


    @Override
    public Place getPlace()
    {
        return PLACE;
    }


    @Override
    protected void onPlaceRequest(PlaceRequest request)
    {
        // Grab the 'id' from the request and use it to get the relevant
        // activity from the server. This allows a tag of '#activity;id=23' to
        // load the activity.
        final String id = request.getParameter("id", null);
        if (id != null)
        {
            // TODO Load the activity from the server
        }
    }
}
