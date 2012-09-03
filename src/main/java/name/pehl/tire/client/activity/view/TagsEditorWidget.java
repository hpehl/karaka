package name.pehl.tire.client.activity.view;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsEditorWidget extends Widget
{
    private final DivElement root;


    public TagsEditorWidget()
    {
        super();
        root = Document.get().createDivElement();
        setElement(root);
        setStyleName("tire-tagsEditor");
    }
}
