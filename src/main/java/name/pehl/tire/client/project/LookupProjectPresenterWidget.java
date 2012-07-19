package name.pehl.tire.client.project;

import static name.pehl.tire.client.model.LookupNamedModelPresenterWidget.SearchMode.CLIENT_SIDE_SEARCH;
import name.pehl.tire.client.model.LookupNamedModelPresenterWidget;
import name.pehl.tire.shared.model.Project;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class LookupProjectPresenterWidget extends LookupNamedModelPresenterWidget<Project>
{
    @Inject
    public LookupProjectPresenterWidget(EventBus eventBus, LookupNamedModelPresenterWidget.MyView view,
            DispatchAsync dispatcher, LookupProjectsHandler lookupProjectsHandler)
    {
        super(eventBus, view, dispatcher, lookupProjectsHandler, "Select a project", CLIENT_SIDE_SEARCH);
    }
}
