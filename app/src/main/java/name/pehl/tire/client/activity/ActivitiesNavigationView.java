package name.pehl.tire.client.activity;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

public interface ActivitiesNavigationView extends View, HasUiHandlers<ActivitiesNavigationUiHandlers>
{
    void updateActivities(Activities activities, ActivitiesNavigationData and);
}
