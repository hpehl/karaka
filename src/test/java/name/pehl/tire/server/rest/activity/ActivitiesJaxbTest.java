package name.pehl.tire.server.rest.activity;

import java.io.IOException;
import java.io.StringWriter;

import name.pehl.tire.server.activity.conotrol.ActivitiesGenerator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import static org.junit.Assert.assertNotNull;

public class ActivitiesJaxbTest
{
    private ObjectMapper mapper;


    @Before
    public void setUp()
    {
        mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        mapper.setAnnotationIntrospector(introspector);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationConfig.Feature.INDENT_OUTPUT, true)
                .configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
    }


    @Test
    public void toJsonMonth() throws JsonGenerationException, JsonMappingException, IOException
    {
        StringWriter buffer = new StringWriter();
        DateMidnight now = DateMidnight.now();
        Activities activities = new ActivitiesBuilder(now, DateTimeZone.getDefault(), MONTH,
                new ActivitiesGenerator().generateMonth(now.year().get(), now.monthOfYear().get())).build();
        mapper.writeValue(buffer, activities);
        String json = buffer.toString();
        assertNotNull(json);
        System.out.println(json);
    }


    @Test
    public void toJsonWeek() throws JsonGenerationException, JsonMappingException, IOException
    {
        StringWriter buffer = new StringWriter();
        DateMidnight now = DateMidnight.now();
        Activities activities = new ActivitiesBuilder(now, DateTimeZone.getDefault(), WEEK,
                new ActivitiesGenerator().generateWeek(now.year().get(), now.weekOfWeekyear().get())).build();
        mapper.writeValue(buffer, activities);
        String json = buffer.toString();
        assertNotNull(json);
        System.out.println(json);
    }
}
