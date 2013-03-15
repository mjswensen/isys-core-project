<jsp:directive.page import="edu.byu.isys413.data.models.*"/>
<%

Customer cust = null;
if(request.getSession().getAttribute("cust") != null) {
	cust = (Customer) request.getSession().getAttribute("cust");
}

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>MyStuff | <%=request.getParameter("title")%></title>
	<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css" rel="stylesheet">
	<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="well well-small">
	<img src="images/meyer_1.png" alt="company logo">
	<% if(cust != null) { %>
	<p class="pull-right">Welcome, <%= cust.getFirstName() %> | <a href="edu.byu.isys413.data.actions.Logout.action">Log out</a></p>
	<% } %>
</div>