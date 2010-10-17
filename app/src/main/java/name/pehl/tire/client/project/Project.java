package name.pehl.tire.client.project;

import name.pehl.piriti.client.json.JsonReader;
import name.pehl.piriti.client.json.JsonWriter;
import name.pehl.tire.client.model.DescriptiveModel;

import com.google.gwt.core.client.GWT;

/**
 * @author $Author$
 * @version $Date$ $Revision: 83
 *          $
 */
public class Project extends DescriptiveModel
{
    // @formatter:off
    interface ProjectReader extends JsonReader<Project> {}
    public static final ProjectReader JSON_READER = GWT.create(ProjectReader.class);

    interface ProjectWriter extends JsonWriter<Project> {}
    public static final ProjectReader JSON_WRITER = GWT.create(ProjectWriter.class);
    // @formatter:on
}
