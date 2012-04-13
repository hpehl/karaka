package name.pehl.tire.server.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;

import name.pehl.tire.server.activity.entity.Time;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TimeJsonSerializer extends JsonSerializer<Time>
{
    @Override
    public void serialize(Time value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeString(new SimpleDateFormat(JacksonConfigurator.DATE_FORMAT).format(value.getDate()));
    }


    @Override
    public Class<Time> handledType()
    {
        return Time.class;
    }
}
