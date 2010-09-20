package name.pehl.tire.client.model;

import java.util.Date;

import name.pehl.piriti.client.json.JsonField;
import name.pehl.piriti.client.json.JsonReader;

import com.google.gwt.core.client.GWT;

public class ItemModel {
	public interface ItemReader extends JsonReader<ItemModel> {
	}

	public static final ItemReader JSON = GWT.create(ItemReader.class);

	@JsonField
	public Long id;

	@JsonField
	public String name;

	@JsonField
	public String owner;

	@JsonField
	public Date creationDate;

}
