<%@include file="/html/init.jsp"%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="addEntry" var="addEntryURL"></portlet:actionURL>

<aui:form action="<%= addEntryURL %>" name="<portlet:namespace />">
	<aui:fieldset>
		<aui:input name="name"></aui:input>
		<aui:input name="email"></aui:input>
		<aui:input name="message"></aui:input>
		<aui:input name="guestbookId" type="hidden" value='<%=ParamUtil.getString(renderRequest, "guestbookId") %>'></aui:input>
		
		<aui:button-row>
			<aui:button type="submit"></aui:button>
			<aui:button type="cancel" onClick="<%= viewURL.toString() %>"></aui:button>
		</aui:button-row>
		
	</aui:fieldset>
</aui:form>




<!-- DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>hello!</h1>
</body>
</html-->