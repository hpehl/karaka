package name.pehl.karaka.client.activity.model;

import name.pehl.piriti.commons.client.Mapping;
import name.pehl.piriti.commons.client.Mappings;
import name.pehl.piriti.json.client.JsonWriter;
import name.pehl.karaka.shared.model.Activity;

@Mappings({@Mapping(value = "pause", convert = DurationConverter.class),
        @Mapping(value = "duration", convert = DurationConverter.class)})
public interface ActivityWriter extends JsonWriter<Activity>
{
}
