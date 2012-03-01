package name.pehl.tire.model;

/**
 * Interface for entities which have some data the user can search for.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-04 18:21:43 +0100 (Do, 04. Nov 2010) $ $Revision: 138 $
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
