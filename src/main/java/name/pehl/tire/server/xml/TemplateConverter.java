package name.pehl.tire.server.xml;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public interface TemplateConverter
{
    String convert(String template, Context context) throws ConverterException;
}
