package name.pehl.tire.client.project;

import name.pehl.piriti.client.json.JsonField;

import com.gwtplatform.annotation.GenDto;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
@GenDto
public class Project
{
    @JsonField
    Long id;

    @JsonField
    String name;

    @JsonField
    String description;
}
