package name.pehl.tire.client.activity.model;

import name.pehl.piriti.commons.client.Mapping;
import name.pehl.piriti.commons.client.Mappings;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.shared.model.Durations;

@Mappings({@Mapping(value = "month", convert = DurationConverter.class),
        @Mapping(value = "week", convert = DurationConverter.class),
        @Mapping(value = "day", convert = DurationConverter.class)})
public interface DurationsReader extends JsonReader<Durations>
{
}
