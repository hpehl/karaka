package name.pehl.tire.client.activity.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

interface ActivityTemplates extends SafeHtmlTemplates
{
    ActivityTemplates INSTANCE = GWT.create(ActivityTemplates.class);


    @Template("<span title=\"{1}\">{0}</span>")
    SafeHtml nameDescription(String name, String description);


    @Template("<mark>{0}</mark>")
    SafeHtml tag(String name);


    @Template("<div class=\"{0}\" style=\"width:40px;\"><span style=\"margin-right:4px;\" title=\"Continue\">{1}</span><span title=\"Delete\">{2}</span></div>")
    SafeHtml actions(String hideActionsClassname, SafeHtml goon, SafeHtml delete);
}
