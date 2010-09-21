package name.pehl.tire.client;

/**
 * The central location of all name tokens for the application. All
 * {@link com.gwtplatform.mvp.client.proxy.ProxyPlace} classes get their tokens
 * from here. This class also makes it easy to use name tokens as a resource
 * within UIBinder xml files.
 * <p>
 * The public static final String is used within the annotation
 * {@link com.gwtplatform.mvp.client.annotations.NameToken}, which can't use a
 * method and the method associated with this field is used within UiBinder
 * which can't access static fields.
 * <p>
 * Also note the exclamation mark in front of the tokens, this is used for
 * search engine crawling support.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class NameTokens
{
    public static final String aboutPage = "!aboutPage";

    public static final String dashboard = "!dashboard";


    public static String getAboutPage()
    {
        return aboutPage;
    }


    public static String getDashboard()
    {
        return dashboard;
    }
}
