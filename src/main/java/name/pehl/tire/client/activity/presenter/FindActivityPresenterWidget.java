package name.pehl.tire.client.activity.presenter;

import name.pehl.tire.client.activity.dispatch.FindActivitiesHandler;
import name.pehl.tire.client.model.DisplayStringFormatter;
import name.pehl.tire.client.model.LookupNamedModelPresenterWidget;
import name.pehl.tire.client.model.NamedModelSuggestion;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class FindActivityPresenterWidget extends LookupNamedModelPresenterWidget<Activity>
{
    @Inject
    public FindActivityPresenterWidget(EventBus eventBus, LookupNamedModelPresenterWidget.MyView view,
            DispatchAsync dispatcher, FindActivitiesHandler findActivitiesHandler)
    {
        super(eventBus, view, dispatcher, findActivitiesHandler, "Select an activity", false);
    }


    @Override
    protected NamedModelSuggestion<Activity> newSuggestionFor(Activity activity, DisplayStringFormatter formatter)
    {
        StringBuilder displayString = new StringBuilder();
        displayString.append(activity.getName());
        if (activity.getDescription() != null)
        {
            displayString.append(": ").append(activity.getDescription());
        }
        return new NamedModelSuggestion<Activity>(activity.getName(), formatter.format(displayString.toString()),
                activity);
    }
}
