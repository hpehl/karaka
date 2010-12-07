package name.pehl.tire.client.activity;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

/**
 * @author $Author$
 * @version $Date$ $Revision: 180
 *          $
 */
@GenEvent
@SuppressWarnings("unused")
public class ActivitiesLoaded
{
    @Order(1)
    private Activities activities;
}
