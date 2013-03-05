<%@page contentType="application/json" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.*"/>
{
  "message": "<font color=red><%=request.getAttribute("message")%></font>",
  "history": [ <%=session.getAttribute("historyString")%> ]
}






















