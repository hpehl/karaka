package name.pehl.karaka.client.activity.dispatch;

import java.util.List;

import name.pehl.karaka.shared.model.Activity;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch
public class FindActivity
{
    @In(1) String query;
    @Out(1) List<Activity> activities;
}
