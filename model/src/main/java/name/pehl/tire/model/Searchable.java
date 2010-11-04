package name.pehl.tire.model;

/**
 * Interface for entities which have some data the user can search for.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public interface Searchable
{
    /**
     * Returns the data to search for. The data need not be normalized. The
     * database layer should take care of this.
     * 
     * @return the data to index.
     */
    String getSearchData();
}
