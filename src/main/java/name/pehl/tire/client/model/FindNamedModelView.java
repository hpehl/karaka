package name.pehl.tire.client.model;

import name.pehl.tire.client.ui.Html5TextBox;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class FindNamedModelView extends ViewWithUiHandlers<FindNamedModelUiHandlers> implements
        FindNamedModelPresenterWidget.MyView
{
    final SuggestOracle suggestOracle;
    final Html5TextBox textBox;
    final SuggestBox suggestBox;


    public FindNamedModelView()
    {
        this.suggestOracle = new NamedModelSuggestOracle();
        this.textBox = new Html5TextBox();
        this.suggestBox = new SuggestBox(suggestOracle, textBox);
    }


    @Override
    public Widget asWidget()
    {
        return suggestBox;
    }


    @Override
    public void setPlaceholder(String placeholder)
    {
        textBox.setPlaceholder(placeholder);
    }

    class NamedModelSuggestOracle extends SuggestOracle
    {
        static final int DELAY = 1000;

        final Timer timer;
        Request currentRequest;
        Callback currentCallback;


        NamedModelSuggestOracle()
        {
            this.timer = new Timer()
            {
                @Override
                public void run()
                {
                    /*
                     * The reason we check for empty string is found at
                     * http://development.lombardi.com/?p=39 -- paraphrased, if
                     * you backspace quickly the contents of the field are
                     * emptied but a query for a single character is still
                     * executed. Workaround for this is to check for an empty
                     * string field here.
                     */
                    if (getUiHandlers() != null)
                    {
                        String query = suggestBox.getText().trim();
                        if (query != null && query.length() != 0)
                        {
                            getUiHandlers().onRequestSuggestions(query, currentRequest, currentCallback);
                        }
                    }
                }
            };
        }


        @Override
        public void requestSuggestions(Request request, Callback callback)
        {
            // This is the method that gets called by the SuggestBox whenever
            // some types into the text field
            currentRequest = request;
            currentCallback = callback;

            // If the user keeps triggering this event (e.g., keeps typing),
            // cancel and restart the timer
            timer.cancel();
            timer.schedule(DELAY);
        }
    }
}
