package name.pehl.tire.client.model;

import java.util.List;

import name.pehl.tire.shared.model.BaseModel;

public interface ModelCache<T extends BaseModel>
{
    T single();


    List<T> list();


    void refresh();
}
