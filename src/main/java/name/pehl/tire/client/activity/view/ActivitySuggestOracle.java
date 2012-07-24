package name.pehl.tire.client.activity.view;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.activity.dispatch.FindActivityAction;
import name.pehl.tire.client.activity.dispatch.FindActivityResult;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.NamedModelSuggestion;
import name.pehl.tire.client.ui.Highlighter;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class ActivitySuggestOracle extends SuggestOracle
{
    static final int DELAY = 500;

    final Timer timer;
    final EventBus eventBus;
    final DispatchAsync dispatcher;

    Request currentRequest;
    Callback currentCallback;


    public ActivitySuggestOracle(EventBus eventBus, DispatchAsync dispatcher)
    {
        super();
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;
        this.timer = new QueryTimer();
    }


    @Override
    public void requestSuggestions(Request request, Callback callback)
    {
        currentRequest = request;
        currentCallback = callback;

        // If the user keeps triggering this event (e.g., keeps typing),
        // cancel and restart the timer
        timer.cancel();
        timer.schedule(DELAY);
    }


    @Override
    public boolean isDisplayStringHTML()
    {
        return true;
    }

    class QueryTimer extends Timer
    {
        @Override
        public void run()
        {
            /*
             * The reason we check for empty string is found at
             * http://development.lombardi.com/?p=39 -- paraphrased, if you
             * backspace quickly the contents of the field are emptied but a
             * query for a single character is still executed. Workaround for
             * this is to check for an empty string field here.
             */
            if (currentRequest != null && currentCallback != null)
            {
                String query = currentRequest.getQuery().trim();
                if (query != null && query.length() != 0)
                {
                    final Highlighter highlighter = new Highlighter(query);
                    dispatcher.execute(new FindActivityAction(query), new TireCallback<FindActivityResult>(eventBus)
                    {
                        @Override
                        public void onSuccess(FindActivityResult result)
                        {
                            List<Activity> activities = result.getActivities();
                            if (!activities.isEmpty())
                            {
                                List<NamedModelSuggestion<Activity>> suggestions = new ArrayList<NamedModelSuggestion<Activity>>();
                                for (Activity activity : activities)
                                {
                                    StringBuilder displayString = new StringBuilder();
                                    displayString.append(activity.getName());
                                    if (activity.getDescription() != null)
                                    {
                                        displayString.append(": ").append(activity.getDescription());
                                    }
                                    NamedModelSuggestion<Activity> suggestion = new NamedModelSuggestion<Activity>(
                                            activity, activity.getName(), highlighter.highlight(displayString
                                                    .toString()));
                                    suggestions.add(suggestion);
                                }
                                currentCallback.onSuggestionsReady(currentRequest, new Response(suggestions));
                            }
                        }
                    });
                }
            }
        }
    }
}
