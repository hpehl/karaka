package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.model.LookupNamedModelPresenterWidget.SearchMode.SERVER_SIDE_SEARCH;
import name.pehl.tire.client.activity.dispatch.LookupActivityAction;
import name.pehl.tire.client.activity.dispatch.LookupActivityHandler;
import name.pehl.tire.client.model.DisplayStringFormatter;
import name.pehl.tire.client.model.LookupNamedModelAction;
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
            DispatchAsync dispatcher, LookupActivityHandler lookupActivityHandler)
    {
        super(eventBus, view, dispatcher, lookupActivityHandler, "Select an activity", SERVER_SIDE_SEARCH);
    }


    @Override
    protected LookupNamedModelAction<Activity> newAction(String query)
    {
        return new LookupActivityAction(query);
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
