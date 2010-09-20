package name.pehl.tire.client.model;

import java.util.List;

import name.pehl.piriti.client.json.JsonField;
import name.pehl.piriti.client.json.JsonReader;

import com.google.gwt.core.client.GWT;

public class ItemsModel
{
    public interface ItemsReader extends JsonReader<ItemsModel>
    {
    }

    public static final ItemsReader JSON = GWT.create(ItemsReader.class);

    @JsonField
    public List<ItemModel> items;
}
