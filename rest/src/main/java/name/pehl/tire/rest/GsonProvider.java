package name.pehl.tire.rest;

import name.pehl.tire.model.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GsonProvider implements Provider<Gson>
{
    private final TimeAdapter timeAdapter;


    @Inject
    public GsonProvider(TimeAdapter timeAdapter)
    {
        this.timeAdapter = timeAdapter;
    }


    @Override
    public Gson get()
    {
        return new GsonBuilder().registerTypeAdapter(Time.class, timeAdapter).create();
    }
}
