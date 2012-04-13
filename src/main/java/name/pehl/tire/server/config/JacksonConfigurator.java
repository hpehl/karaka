package name.pehl.tire.server.config;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;

@Provider
@Consumes({MediaType.APPLICATION_JSON, "text/json"})
@Produces({MediaType.APPLICATION_JSON, "text/json"})
public class JacksonConfigurator extends ResteasyJacksonProvider
{
    static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss.SSS Z";
    static final Logger log = Logger.getLogger(JacksonConfigurator.class.getName());


    public JacksonConfigurator()
    {
        super();
        log.info("Configuring Jackson");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true)
                .configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(DATE_FORMAT));

        setMapper(mapper);
    }
}
