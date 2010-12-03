package name.pehl.tire.rest.activity;

import java.lang.reflect.Type;

import name.pehl.tire.model.Activity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityAdapter implements JsonSerializer<Activity>
{
    /**
     * @param src
     * @param typeOfSrc
     * @param context
     * @return
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     *      java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(Activity src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject activityJson = new JsonObject();
        activityJson.add("start", context.serialize(src.getStart()));
        activityJson.add("end", context.serialize(src.getEnd()));
        activityJson.add("pause", context.serialize(src.getPause()));
        // Minutes would not be generated without this adapter
        activityJson.add("minutes", context.serialize(src.getMinutes()));
        activityJson.add("billable", context.serialize(src.isBillable()));
        activityJson.add("status", context.serialize(src.getStatus()));
        activityJson.add("tags", context.serialize(src.getTags()));
        activityJson.add("project", context.serialize(src.getProject()));
        return activityJson;
    }
}
