package name.pehl.tire.rest.activity;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import name.pehl.tire.model.Time;
import name.pehl.tire.rest.DateTimeFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class TimeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time>
{
    private final SimpleDateFormat sdf;


    @Inject
    public TimeAdapter(@DateTimeFormat String dateTimeFormat)
    {
        this.sdf = new SimpleDateFormat(dateTimeFormat);
    }


    /**
     * @param src
     * @param typeOfSrc
     * @param context
     * @return
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     *      java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context)
    {
        return new JsonPrimitive(sdf.format(src.getDate()));
    }


    /**
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     *      java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        String date = json.getAsJsonPrimitive().getAsString();
        Time time = null;
        try
        {
            time = new Time(sdf.parse(date));
        }
        catch (ParseException e)
        {
            // TODO Exception handling
        }
        return time;
    }
}
