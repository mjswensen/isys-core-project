<jsp:directive.page import="java.util.*"/> 

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="My Very Cool Site" />
</jsp:include>

<%
  if (request.getAttribute("message") != null) {
      out.println("<p>");
      out.println(request.getAttribute("message"));
      out.println("</p>");
  }//if


%>

<p>
<form method="post" action="edu.byu.isys413.data.actions.NumberGame.action">
    Guess between 1 and 10:
    <input type="text" size="10" name="guess">
    <input type="submit" name="mysubmitter" value="Submit Guess">
</form>
</p>

<div>History:</div>
<ol>
<% for (String guess: (List<String>)session.getAttribute("history")) { %>
  <li><%=guess%></li>
<% } %>
</ol>

<jsp:include page="/footer.jsp"/>
