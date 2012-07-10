package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.dispatch.FindActivitiesHandler;
import name.pehl.tire.client.model.FindNamedModelPresenterWidget;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class FindActivityPresenterWidget extends FindNamedModelPresenterWidget<Activity>
{
    @Inject
    public FindActivityPresenterWidget(EventBus eventBus, FindNamedModelPresenterWidget.MyView view,
            DispatchAsync dispatcher, FindActivitiesHandler findActivitiesHandler)
    {
        super(eventBus, view, dispatcher, findActivitiesHandler, "Select an activity", false);
    }
}
