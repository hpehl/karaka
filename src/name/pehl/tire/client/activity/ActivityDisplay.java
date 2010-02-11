package name.pehl.tire.client.activity;

import java.util.Date;

import name.pehl.tire.client.mvp.DescriptiveDisplay;
import name.pehl.tire.client.mvp.NewEditDeleteDisplay;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface ActivityDisplay extends DescriptiveDisplay, NewEditDeleteDisplay
{
    HasValue<Date> getStart();


    HasValue<Date> getEnd();


    HasValue<Boolean> isPause();


    void setDuration(long duration);
}
