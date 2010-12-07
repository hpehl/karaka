package name.pehl.tire.client.activity;

import name.pehl.tire.client.activity.ActivitiesNavigation.Unit;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;



public interface ActivitiesNavigationView extends View, HasUiHandlers<ActivitiesNavigationUiHandlers>
{
    void updateActivities(Activities activities, Unit unit);
}