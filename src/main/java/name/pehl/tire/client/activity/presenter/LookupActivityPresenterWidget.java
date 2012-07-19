package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.model.LookupNamedModelPresenterWidget.SearchMode.SERVER_SIDE_SEARCH;
import name.pehl.tire.client.activity.dispatch.LookupActivitiesHandler;
import name.pehl.tire.client.model.DisplayStringFormatter;
import name.pehl.tire.client.model.LookupNamedModelPresenterWidget;
import name.pehl.tire.client.model.NamedModelSuggestion;
import name.pehl.tire.shared.model.Activity;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class LookupActivityPresenterWidget extends LookupNamedModelPresenterWidget<Activity>
{
    @Inject
    public LookupActivityPresenterWidget(EventBus eventBus, LookupNamedModelPresenterWidget.MyView view,
            DispatchAsync dispatcher, LookupActivitiesHandler lookupActivitiesHandler)
    {
        super(eventBus, view, dispatcher, lookupActivitiesHandler, "Select an activity", SERVER_SIDE_SEARCH);
    }


    @Override
    protected NamedModelSuggestion<Activity> newSuggestionFor(Activity activity,
            DisplayStringFormatter displayStringFormatter)
    {
        StringBuilder displayString = new StringBuilder();
        displayString.append(activity.getName());
        if (activity.getDescription() != null)
        {
            displayString.append(": ").append(activity.getDescription());
        }
        return new NamedModelSuggestion<Activity>(activity, activity.getName(),
                displayStringFormatter.format(displayString.toString()));
    }
}
