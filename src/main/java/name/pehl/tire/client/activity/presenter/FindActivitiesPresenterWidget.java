package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.dispatch.FindActivitiesHandler;
import name.pehl.tire.client.model.FindNamedModelsPresenterWidget;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class FindActivitiesPresenterWidget extends FindNamedModelsPresenterWidget<Activity>
{
    public interface MyView extends FindNamedModelsPresenterWidget.MyView<Activity>
    {
    }


    @Inject
    public FindActivitiesPresenterWidget(EventBus eventBus, FindActivitiesPresenterWidget.MyView view,
            DispatchAsync dispatcher, FindActivitiesHandler findActivitiesHandler)
    {
        super(eventBus, view, dispatcher, findActivitiesHandler, "Select an activity", false);
    }
}
