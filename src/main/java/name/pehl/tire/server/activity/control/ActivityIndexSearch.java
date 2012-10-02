package name.pehl.tire.server.activity.control;

import com.google.appengine.api.search.*;
import com.google.appengine.api.search.QueryOptions.Builder;
import com.google.appengine.api.search.SortExpression.SortDirection;
import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.search.DescriptiveEntityIndexSearch;
import name.pehl.tire.server.search.IndexName;
import name.pehl.tire.server.search.IndexSearch;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityIndexSearch extends DescriptiveEntityIndexSearch<Activity> implements IndexSearch<Activity>
{
    @Inject @IndexName("activity") Index index;


    @Override
    protected Index getIndex()
    {
        return index;
    }


    @Override
    @SuppressWarnings("deprecation")
    protected Document.Builder documentBuilderFor(Activity entity)
    {
        Date start = entity.getStart().getDateTime().toDate();
        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(0);
        return super.documentBuilderFor(entity).addField(Field.newBuilder().setName("start").setDate(start));
    }


    @Override
    protected Builder queryOptionsBuilder()
    {
        return super.queryOptionsBuilder().setSortOptions(
                SortOptions.newBuilder().addSortExpression(
                        SortExpression.newBuilder().setExpression("start").setDirection(SortDirection.DESCENDING)
                                .setDefaultValue("")));
    }
}
