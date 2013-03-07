<jsp:include page="/header.jsp"/>

<div class="container">
	<div class="row">
		<div class="span4 offset4">
			<div class="alert alert-<%= request.getAttribute("messageType") %>">
				<%= request.getAttribute("message") %>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/footer.jsp"/>
