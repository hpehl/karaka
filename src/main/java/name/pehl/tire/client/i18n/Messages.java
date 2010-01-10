package name.pehl.tire.client.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/pehlh/Projekte/TiRe/impl/name.pehl.tire/war/WEB-INF/classes/name/pehl/tire/client/i18n/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Welcome.  The current time is {0}.".
   * 
   * @return translated "Welcome.  The current time is {0}."
   */
  @DefaultMessage("Welcome.  The current time is {0}.")
  @Key("welcome")
  String welcome(String arg0);
}
