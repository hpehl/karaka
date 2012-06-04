package name.pehl.tire.shared.model;

import java.util.Comparator;

import com.google.common.collect.ComparisonChain;

public class ActivityComparator implements Comparator<Activity>
{
    @Override
    public int compare(Activity left, Activity right)
    {
        return ComparisonChain.start().compare(right.start, left.start).compare(right.id, left.id).result();
    }
}
