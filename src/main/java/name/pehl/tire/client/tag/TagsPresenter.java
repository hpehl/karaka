package name.pehl.tire.client.tag;

import java.util.List;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.tag.TagAction.Action;
import name.pehl.tire.shared.model.Tag;

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
    // ---------------------------------------------------------- inner classes

    @ProxyCodeSplit
    @NameToken(NameTokens.tags)
    public interface MyProxy extends ProxyPlace<TagsPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<TagsUiHandlers>
    {
        void updateTags(List<Tag> tags);
    }

    // ------------------------------------------------------- (static) members

    static final Logger logger = Logger.getLogger(TagsPresenter.class.getName());

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
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onTagAction(final Action action, final Tag tag)
    {
        // TODO Auto-generated method stub
    }
}
