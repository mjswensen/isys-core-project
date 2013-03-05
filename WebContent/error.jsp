<jsp:directive.page import="edu.byu.isys413.data.web.*"/> 

<%
   // if we get to this page, we have a web exception in the request
   // (Tomcat puts it there for us per web.xml settings)
   // let's just make sure it is there
   WebException exc = (WebException)request.getAttribute("javax.servlet.error.exception");
   if (exc == null) {
     throw new JspException("error.jsp cannot be called directly!");
   }
%>

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Error" />
</jsp:include>

This is an example error page.  If you look at web.xml, you'll see that
edu.byu.isys413.web.WebException is mapped to /error.jsp.  This tells
Tomcat to call /error.jsp automatically whenever a WebException is 
thrown.  
<p>
Effectively, you can <tt>throw new WebException("Error")</tt> whenever
you want to show the error page.
<p>
You obviously should replace the text of this page with something
nicer for your clients to see.
<p>
The error was as follows:
<p>

<div style="color:#FF0000" align="center">
    <%=exc.getMessage()%>
</div>    

<jsp:include page="/footer.jsp"/>
