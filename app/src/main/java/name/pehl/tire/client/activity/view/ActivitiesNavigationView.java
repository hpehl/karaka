package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.presenter.ActivitiesNavigationUiHandlers;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

public interface ActivitiesNavigationView extends View, HasUiHandlers<ActivitiesNavigationUiHandlers>
{
    void updateActivities(Activities activities);
}
