package name.pehl.tire.client.model;

import java.util.List;

import name.pehl.tire.shared.model.BaseModel;

public interface ModelCache<T extends BaseModel> extends Iterable<T>
{
    T single();


    List<T> list();


    boolean isEmpty();


    void refresh();
}
