package name.pehl.tire.client.tag;

import name.pehl.piriti.client.json.JsonReader;
import name.pehl.piriti.client.json.JsonWriter;
import name.pehl.tire.client.model.NamedModel;

import com.google.gwt.core.client.GWT;

/**
 * @author $Author$
 * @version $Date$ $Revision: 83
 *          $
 */
public class Tag extends NamedModel
{
    // @formatter:off
    interface ProjectReader extends JsonReader<Tag> {}
    public static final ProjectReader JSON_READER = GWT.create(ProjectReader.class);

    interface ProjectWriter extends JsonWriter<Tag> {}
    public static final ProjectReader JSON_WRITER = GWT.create(ProjectWriter.class);
    // @formatter:on
}
