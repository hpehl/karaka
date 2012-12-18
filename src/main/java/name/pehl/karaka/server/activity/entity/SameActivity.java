package name.pehl.karaka.server.activity.entity;

import name.pehl.karaka.server.project.entity.Project;

/**
 * Compares activities by name, description and project instead by id. Can be used as key in maps to filter
 * activities.
 * @author Harald Pehl
 */
public class SameActivity
{
    private final String name;
    private final String description;
    private final Project project;


    public SameActivity(final Activity activity)
    {
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.project = activity.getProject() != null ? activity.getProject().get() : null;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) { return true; }
        if (!(o instanceof SameActivity)) { return false; }

        SameActivity that = (SameActivity) o;

        if (!name.equals(that.name)) { return false; }
        if (description != null ? !description.equals(that.description) : that.description != null) { return false; }
        if (project != null ? !project.equals(that.project) : that.project != null) { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }
}
