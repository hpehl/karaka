package name.pehl.karaka.client.activity.view;

import name.pehl.karaka.client.activity.presenter.NewActivityUiHandlers;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestOracle;

public class ActivitySuggestOracle extends SuggestOracle
{
    static final int DELAY = 300;

    final Timer timer;
    NewActivityUiHandlers uiHandlers;
    Request currentRequest;
    Callback currentCallback;


    public ActivitySuggestOracle()
    {
        super();
        this.timer = new QueryTimer();
    }


    public void setUiHandlers(NewActivityUiHandlers uiHandlers)
    {
        this.uiHandlers = uiHandlers;
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
            if (currentRequest != null && currentCallback != null && uiHandlers != null)
            {
                String query = currentRequest.getQuery().trim();
                if (query != null && query.length() != 0)
                {
                    uiHandlers.onFindActivity(currentRequest, currentCallback);
                }
            }
        }
    }
}
