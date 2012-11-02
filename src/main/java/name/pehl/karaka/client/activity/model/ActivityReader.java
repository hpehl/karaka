package name.pehl.karaka.client.activity.model;

import name.pehl.piriti.commons.client.Mapping;
import name.pehl.piriti.commons.client.Mappings;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.shared.model.Activity;

@Mappings({@Mapping(value = "start", setter = ActivityStartSetter.class),
        @Mapping(value = "pause", convert = DurationConverter.class),
        @Mapping(value = "duration", convert = DurationConverter.class)})
public interface ActivityReader extends JsonReader<Activity>
{
}
