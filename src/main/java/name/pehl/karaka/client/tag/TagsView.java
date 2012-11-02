package name.pehl.karaka.client.tag;

import java.util.List;

import name.pehl.karaka.client.resources.I18n;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.shared.model.Tag;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class TagsView extends ViewWithUiHandlers<TagsUiHandlers> implements TagsPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, TagsView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final I18n i18n;
    final Widget widget;

    @UiField(provided = true) TagsTable tagsTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public TagsView(final Binder binder, final I18n i18n, final Resources resources,
            final TableResources commonTableResources)
    {
        this.i18n = i18n;
        this.tagsTable = new TagsTable(resources, commonTableResources);
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateTags(final List<Tag> tags)
    {
        tagsTable.update(tags);
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("tagsTable")
    public void onProjectAction(final TagActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onTagAction(event.getAction(), event.getTag());
        }
    }
}
