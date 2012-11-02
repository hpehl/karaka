package name.pehl.karaka.shared.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DurationAdapter extends XmlAdapter<Long, Duration>
{
    @Override
    public Duration unmarshal(Long v) throws Exception
    {
        return new Duration(v);
    }


    @Override
    public Long marshal(Duration v) throws Exception
    {
        return v.getTotalMinutes();
    }
}
