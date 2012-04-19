package name.pehl.tire.server.sampledata;

public class IdGenerator
{
    static long nextId = 0;


    public long nextId()
    {
        return nextId++;
    }
}
