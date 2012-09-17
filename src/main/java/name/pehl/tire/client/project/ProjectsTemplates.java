package name.pehl.tire.client.project;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface ProjectsTemplates extends SafeHtmlTemplates
{
    @Template("<div class=\"{0}\"><span title=\"Delete\">{1}</span></div>")
    SafeHtml actions(String hideActionsClassname, SafeHtml delete);
}
