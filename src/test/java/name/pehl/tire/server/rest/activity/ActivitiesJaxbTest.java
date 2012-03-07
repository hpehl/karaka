package name.pehl.tire.server.rest.activity;

import java.io.IOException;

import name.pehl.tire.shared.model.TimeUnit;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.junit.Before;
import org.junit.Test;

public class ActivitiesJaxbTest
{
    private ObjectMapper mapper;


    @Before
    public void setUp()
    {
        mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        mapper.setAnnotationIntrospector(introspector);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false).configure(
                SerializationConfig.Feature.INDENT_OUTPUT, false);
        mapper = mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
    }


    @Test
    public void toJson() throws JsonGenerationException, JsonMappingException, IOException
    {
        mapper.writeValue(System.out, new Activities(1973, 0, 9, 0, 31, 0, TimeUnit.MONTH));
    }
}
