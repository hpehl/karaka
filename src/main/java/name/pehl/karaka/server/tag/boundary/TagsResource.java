package name.pehl.karaka.server.tag.boundary;

import name.pehl.karaka.server.tag.control.TagConverter;
import name.pehl.karaka.server.tag.control.TagRepository;
import name.pehl.karaka.server.tag.entity.Tag;
import org.jboss.resteasy.annotations.cache.Cache;
import org.jboss.resteasy.spi.NotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Supported methods:
 * <ul>
 * <li>GET /tags/: List all tags
 * <li>POST: Create a new tag
 * <li>PUT /tags/{id}: Update an existing tag
 * <li>DELETE /tags/{id}: Delete an existing tag
 * </ul>
 * 
 * @todo implement ETag
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/tags")
@Cache(maxAge = 36000)
@Produces(APPLICATION_JSON)
public class TagsResource
{
    @Inject TagRepository repository;
    @Inject TagConverter converter;


    @GET
    public List<name.pehl.karaka.shared.model.Tag> list()
    {
        List<Tag> tags = repository.list();
        if (tags.isEmpty())
        {
            throw new NotFoundException("No tags found");
        }
        List<name.pehl.karaka.shared.model.Tag> result = new ArrayList<name.pehl.karaka.shared.model.Tag>();
        for (Tag tag : tags)
        {
            result.add(converter.toModel(tag));
        }
        return result;
    }
}
