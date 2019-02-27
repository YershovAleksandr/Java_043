<%@include file="/html/init.jsp"%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<portlet:actionURL name="addGuestbook" var="addGuestbookURL" />

<aui:form action="<%=addGuestbookURL%>" name="<portlet:namespace />">
	<aui:fieldset>
		<aui:input name="name" />
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" />
		<aui:button type="cancel" onClick="<%=viewURL%>" />
	</aui:button-row>
</aui:form>