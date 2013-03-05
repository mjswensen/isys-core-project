<jsp:directive.page import="java.util.*"/>

<jsp:include page="/header.jsp">
  <jsp:param name="title" value="My Very Cool Site" />
</jsp:include>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<!-- We'll put the message here -->
<div align="center" id="message">Message Here</div>

<p>
Guess between 1 and 10:
<input type="text" size="10" id="guess">
<input id="submitButton" type="button" value="Submit Guess">
</p>

<!-- We'll add the history here -->
<div>History (note the page does not refresh and this still gets updated):</div>
<ul id="history">
</ul>



<script type="text/javascript">
  $(function() {  // this function runs as soon as the web page is "ready"
    /** 
    * Submits the guess to the server.  This is the event code, very much
    * like an actionPerformed in Java.
    */
    $('#submitButton').click(function() {  // define the handler function for click event inline (it doesn't need a name because we're just using it one time)
      // get the value of the guess element
      var guessValue = $("#guess").val();
      
      // send to the server (this is relative to our current page)
      $.ajax({
        url: "edu.byu.isys413.data.actions.AjaxGame.action",
        data: { // this is an embedded JSON object we'll send to server
          guess: guessValue,
        },
        dataType: 'json',  // tell JQuery to expect JSON back from the server
        success: function(ret) {  // this is called as soon as the server returns
          $("#message").html(ret.message);  // set the message
          console.log(ret.message);
          $("#history").html(""); // clear the history <li> children (this isn't efficient, just an example)
          console.log(ret.history);
          for (var i = 0; i < ret.history.length; i++) { 
            console.log("appending");
            $("#history").append("<li>" + ret.history[i] + "</li>"); // reappend the entire history
          }//for
        }//success
      });//ajax

      // change the history div color, just 'cause we can
      var randhex = (Math.round(0xFFFFFF * Math.random()).toString(16) + "000000").replace(/([a-f0-9]{6}).+/, "#$1").toUpperCase();
      $("#history").css("background", randhex);

    });//click
  });//ready
</script>



<jsp:include page="/footer.jsp"/>
