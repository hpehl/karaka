package name.pehl.karaka.client.tag;

import name.pehl.karaka.client.tag.TagAction.Action;
import name.pehl.karaka.shared.model.Tag;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 169
 *          $
 */
public interface TagsUiHandlers extends UiHandlers
{
    void onTagAction(Action action, Tag tag);
}
