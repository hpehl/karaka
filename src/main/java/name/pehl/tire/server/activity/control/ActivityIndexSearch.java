package name.pehl.tire.server.activity.control;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.search.DescriptiveEntityIndexSearch;
import name.pehl.tire.server.search.IndexName;
import name.pehl.tire.server.search.IndexSearch;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.QueryOptions.Builder;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;

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
    protected Document.Builder documentBuilderFor(Activity entity)
    {
        return super.documentBuilderFor(entity).addField(
                Field.newBuilder().setName("start").setDate(Field.date(entity.getStart().getDateTime().toDate())));
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
