package name.pehl.karaka.client.tag;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.tag.TagAction.Action;
import name.pehl.karaka.shared.model.Tag;

import java.util.List;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none</li>
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 22:35:01 +0100 (Mi, 01. Dez 2010) $ $Revision: 85
 *          $
 */
public class TagsPresenter extends Presenter<TagsPresenter.MyView, TagsPresenter.MyProxy> implements TagsUiHandlers
{
    @ProxyCodeSplit
    @NameToken(NameTokens.tags)
    public interface MyProxy extends ProxyPlace<TagsPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<TagsUiHandlers>
    {
        void updateTags(List<Tag> tags);
    }

    final TagsCache tagsCache;


    @Inject
    public TagsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final TagsCache tagsCache)
    {
        super(eventBus, view, proxy);
        this.tagsCache = tagsCache;
        getView().setUiHandlers(this);
    }


    // ---------------------------------------------------- presenter lifecycle

    @Override
    public void prepareFromRequest(final PlaceRequest placeRequest)
    {
        super.prepareFromRequest(placeRequest);
        getView().updateTags(tagsCache.list());
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_MainContent, this);
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onTagAction(final Action action, final Tag tag)
    {
        // TODO Auto-generated method stub
    }
}
