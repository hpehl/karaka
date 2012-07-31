package name.pehl.tire.client.activity.model;

import name.pehl.piriti.commons.client.Mapping;
import name.pehl.piriti.commons.client.Mappings;
import name.pehl.piriti.json.client.JsonWriter;
import name.pehl.tire.shared.model.Activity;

@Mappings({@Mapping(value = "pause", convert = DurationConverter.class),
        @Mapping(value = "minutes", convert = DurationConverter.class)})
public interface ActivityWriter extends JsonWriter<Activity>
{
}
