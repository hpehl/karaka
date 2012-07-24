package name.pehl.tire.client.tag;

import java.util.List;

import name.pehl.tire.shared.model.Tag;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class GetTags
{
    @Out(1) List<Tag> tags;
}
