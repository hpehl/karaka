package name.pehl.tire.client.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

interface ProjectsTemplates extends SafeHtmlTemplates
{
    ProjectsTemplates INSTANCE = GWT.create(ProjectsTemplates.class);


    @Template("<div class=\"{0}\" style=\"width:16px;\"><span title=\"Delete\">{1}</span></div>")
    SafeHtml actions(String hideActionsClassname, SafeHtml delete);
}
