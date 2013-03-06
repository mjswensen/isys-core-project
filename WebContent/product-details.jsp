<jsp:include page="/header.jsp">
	<jsp:param name="title" value="DYN - PRODUCT NAME" />
</jsp:include>

<div class="container">
	<div class="row">
		<div class="span4 offset4">
			<h1><%= "PRODUCT_NAME" %></h1>
			<p><%= "PRODUCT_DESCRIPTION" %></p>
			<ul class="nav nav-tabs">
				<li class="active"><a href="#new-product" data-toggle="tab">New</a></li>
				<li><a href="#used-product" data-toggle="tab">Used</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="new-product">
					<form action="purchase-confirmation.html">
						<fieldset>
							<legend>New</legend>
							<label for="store">Store - Quantity</label>
							<select name="store">
								<option value=""><%= "DYN - SOTRE (QTY AVAILABLE)" %></option>
							</select>
							<label for="quantity">Quantity</label>
							<input type="text" name="quantity">
						</fieldset>
						<fieldset>
							<label for="shipping" class="radio inline">
								<input type="radio" name="shipping" value="in-store"> In-store pickup
							</label>
							<label for="shipping" class="radio inline">
								<input type="radio" name="shipping" value="standard"> Standard shipping
							</label>
						</fieldset>
						<fieldset>
							<button class="btn btn-primary">Purchase</button>
						</fieldset>
					</form>
				</div>
				<div class="tab-pane" id="used-product">
					<form action="purchase-confirmation.html">
						<fieldset>
							<legend>Used</legend>
							<label for="product_id">Product - Location</label>
							<select name="product_id">
								<option value=""><%= "DYN - PRODUCT NAME (STORE)" %></option>
							</select>
						</fieldset>
						<fieldset>
							<label for="shipping" class="radio inline">
								<input type="radio" name="shipping" value="in-store"> In-store pickup
							</label>
							<label for="shipping" class="radio inline">
								<input type="radio" name="shipping" value="standard"> Standard shipping
							</label>
						</fieldset>
						<fieldset>
							<button class="btn btn-primary">Purchase</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/footer.jsp" />
