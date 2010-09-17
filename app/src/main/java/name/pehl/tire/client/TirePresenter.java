package name.pehl.tire.client;

import name.pehl.tire.client.command.CreateItemAction;
import name.pehl.tire.client.command.CreateItemActionResult;
import name.pehl.tire.client.command.GetAllItemsAction;
import name.pehl.tire.client.command.GetAllItemsActionResult;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class TirePresenter extends Presenter<TirePresenter.MyView, TirePresenter.MyProxy>
{
    class CreateCallback implements AsyncCallback<CreateItemActionResult>
    {
        @Override
        public void onFailure(final Throwable caught)
        {
        }


        @Override
        public void onSuccess(final CreateItemActionResult result)
        {
            // we omit to check if the creation was a result ...
            view.getNewName().setValue("");
            dispatch.execute(new GetAllItemsAction(), new GetAllCallback());
        }
    }

    class CreateClickHandler implements ClickHandler
    {
        @Override
        public void onClick(final ClickEvent event)
        {
            dispatch.execute(new CreateItemAction(getView().getNewName().getValue()), new CreateCallback());
        }
    }

    class GetAllCallback implements AsyncCallback<GetAllItemsActionResult>
    {
        @Override
        public void onFailure(final Throwable caught)
        {
        }


        @Override
        public void onSuccess(final GetAllItemsActionResult result)
        {
            final MyView v = getView();

            v.clearTable();
            for (int i = 0; i < result.getItems().items.size(); i++)
            {
                // TODO why is it null?
                // result.getItems().items.get(i).id.toString(),
                // TODO why is it null?
                // result.getItems().items.get(i).creationDate.toString()
                v.addItemInTable("", result.getItems().items.get(i).name, "", result.getItems().items.get(i).owner);
            }

        }
    }

    /**
     * {@link TirePresenter}'s proxy.
     */
    @ProxyStandard
    @NameToken(nameToken)
    public interface MyProxy extends Proxy<TirePresenter>, Place
    {
    }

    /**
     * {@link TirePresenter}'s view.
     */
    public interface MyView extends View
    {
        void addItemInTable(String itemId, String itemName, String creationDate, String owner);


        void clearTable();


        HasClickHandlers getNewItemButton();


        HasValue<String> getNewName();


        void setError(String errorText);
    }

    public static final String nameToken = "main";

    private final DispatchAsync dispatch;


    @Inject
    public TirePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final PlaceManager placeManager, final DispatchAsync dispatch)
    {
        super(eventBus, view, proxy);
        this.dispatch = dispatch;
    }


    @Override
    protected void onBind()
    {
        super.onBind();
        view.getNewItemButton().addClickHandler(new CreateClickHandler());
    }


    @Override
    protected void revealInParent()
    {
        RevealRootContentEvent.fire(eventBus, this);
    }

}
