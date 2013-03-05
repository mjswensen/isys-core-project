<jsp:include page="/header.jsp">
  <jsp:param name="title" value="My Very Cool Site" />
</jsp:include>

  <p>Welcome to our site!  It's exciting to have you here as a customer.
     We majored in "Open" at BYU, and we started a business in it!  What do
     we sell?  That depends on what you want.  We're open!  Just let us
     know.  What does our business do for business?  Again, we're open!
     What a perfect major.
  </p> 
  <p>
    Of course, right now we  just have this cool number guessing game:
    <ul>
        <li><a href="edu.byu.isys413.data.actions.NumberGame.action">Number Game (regular version)</a><br>&nbsp;</li>
        <li><a href="ajaxgame_main.jsp">Number Game (ajax version)</a></li>
    </ul>
  </p>

<jsp:include page="/footer.jsp"/>
