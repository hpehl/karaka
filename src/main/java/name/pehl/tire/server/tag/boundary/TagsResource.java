package name.pehl.tire.server.tag.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.tag.control.TagConverter;
import name.pehl.tire.server.tag.control.TagRepository;
import name.pehl.tire.server.tag.entity.Tag;

import org.jboss.resteasy.spi.NotFoundException;

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
@Produces(MediaType.APPLICATION_JSON)
public class TagsResource
{
    @Inject TagRepository repository;
    @Inject TagConverter converter;


    @GET
    public List<name.pehl.tire.shared.model.Tag> list()
    {
        PageResult<Tag> tags = repository.list();
        if (tags.isEmpty())
        {
            throw new NotFoundException("No tags found");
        }
        List<name.pehl.tire.shared.model.Tag> result = new ArrayList<name.pehl.tire.shared.model.Tag>();
        for (Tag tag : tags)
        {
            result.add(converter.toModel(tag));
        }
        return result;
    }
}
