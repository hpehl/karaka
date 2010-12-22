package com.google.gwt.user.cellview.client;

import com.google.gwt.view.client.ProvidesKey;

/**
 * Overwritten {@link CellTable} with the following changes (as suggested by <a
 * href=
 * "https://groups.google.com/d/topic/google-web-toolkit/WiU2HW6vaSw/discussion"
 * >https://groups.google.com/d/topic/google-web-toolkit/WiU2HW6vaSw/discussion
 * </a>)
 * <ul>
 * <li>Changed visibility of
 * {@link #setLoadingState(com.google.gwt.user.cellview.client.HasDataPresenter.LoadingState)}
 * from package to public
 * </ul>
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TirReCellTable<T> extends CellTable<T>
{
    public TirReCellTable()
    {
    }


    public TirReCellTable(int pageSize)
    {
        super(pageSize);
    }


    public TirReCellTable(ProvidesKey<T> keyProvider)
    {
        super(keyProvider);
    }


    public TirReCellTable(int pageSize, com.google.gwt.user.cellview.client.CellTable.Resources resources)
    {
        super(pageSize, resources);
    }


    public TirReCellTable(int pageSize, ProvidesKey<T> keyProvider)
    {
        super(pageSize, keyProvider);
    }


    public TirReCellTable(int pageSize, com.google.gwt.user.cellview.client.CellTable.Resources resources,
            ProvidesKey<T> keyProvider)
    {
        super(pageSize, resources, keyProvider);
    }


    @Override
    public void setLoadingState(HasDataPresenter.LoadingState state)
    {
        super.setLoadingState(state);
    }
}
