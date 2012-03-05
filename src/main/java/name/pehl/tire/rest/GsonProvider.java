package name.pehl.tire.rest;

import name.pehl.tire.rest.activity.ActivityAdapter;
import name.pehl.tire.rest.activity.TimeAdapter;
import name.pehl.tire.server.model.Activity;
import name.pehl.tire.server.model.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * TODO Add adapters for other models TODO "Inject" the current user in
 * JsonDeserializer<T>.deserialize()
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GsonProvider implements Provider<Gson>
{
    private final String dateTimeFormat;
    private final TimeAdapter timeAdapter;
    private final ActivityAdapter activityAdapter;


    @Inject
    public GsonProvider(@DateTimeFormat String dateTimeFormat, ActivityAdapter activityAdapter, TimeAdapter timeAdapter)
    {
        this.dateTimeFormat = dateTimeFormat;
        this.activityAdapter = activityAdapter;
        this.timeAdapter = timeAdapter;
    }


    @Override
    public Gson get()
    {
        return new GsonBuilder().setDateFormat(dateTimeFormat).registerTypeAdapter(Activity.class, activityAdapter)
                .registerTypeAdapter(Time.class, timeAdapter).create();
    }
}
