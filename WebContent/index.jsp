<jsp:include page="/header.jsp">
	<jsp:param name="title" value="Login" />
</jsp:include>

<div class="container">
	<div class="row">
		<div class="span4 offset4">
			<form action="edu.byu.isys413.data.actions.Login.action" method="post">
				<legend>Log in</legend>
				<fieldset>
					<input type="hidden" name="format" value="html">
					<label for="email">Email*</label>
					<input type="text" name="email" required>
					<label for="password">Password*</label>
					<input type="password" name="password" required>
				</fieldset>
				<fieldset>
					<button class="btn btn-primary">Log In</button>
					<a href="#new-account" data-toggle="modal">Create New Account</a>
				</fieldset>
			</form>
		</div>
	</div>
</div>
<div class="modal hide fade" id="new-account">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>New Account</h3>
	</div>
	<div class="modal-body">
		<form action="edu.byu.isys413.data.actions.CreateAndVerifyAccount.action" method="post">
			<fieldset>
				<legend>Personal</legend>
				<label for="firstname">First Name</label>
				<input type="text" name="firstname" required>
				<label for="lastname">Last Name</label>
				<input type="text" name="lastname" required>
				<label for="phone">Phone</label>
				<input type="text" name="phone">
				<label for="email">Email</label>
				<input type="text" name="email" required>
				<span class="help-block">Your email address will be your username to log into this site.</span>
				<label for="password">Password</label>
				<input type="password" name="password" required>
				<legend>Shipping</legend>
				<label for="address1">Address</label>
				<input type="text" name="address1" required>
				<label for="address2">Address Line 2</label>
				<input type="text" name="address2">
				<label for="city">City</label>
				<input type="text" name="city" required>
				<label for="state">State</label>
				<input type="text" name="state" required>
				<label for="zip">Zip</label>
				<input type="text" name="zip" required>
				<legend>Billing</legend>
				<label for="creditcard">Credit Card Number</label>
				<input type="text" name="creditcard" required>
			</fieldset>
			<fieldset>
				<button type="submit" class="btn btn-primary pull-right">Submit</button>
			</fieldset>
		</form>
	</div>
	<div class="modal-footer">
		<a data-dismiss="modal" class="btn">Cancel</a>
	</div>
</div>

<jsp:include page="/footer.jsp" />
