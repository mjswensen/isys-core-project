<jsp:directive.page import="edu.byu.isys413.data.*"/>
<jsp:directive.page import="java.util.List"/>

<jsp:include page="/header.jsp">
	<jsp:param name="title" value="Product Details" />
</jsp:include>

<%

ConceptualProduct cp = (ConceptualProduct) request.getAttribute("cp");
List<StoreProduct> sps = (List<StoreProduct>) request.getAttribute("sps");
List<ForSale> fss = (List<ForSale>) request.getAttribute("fss");

%>

<div class="container">
	<div class="row">
		<div class="span4 offset4">
			<h1><%= cp.getName() %></h1>
			<p><%= cp.getDescription() %></p>
			<ul class="nav nav-tabs">
				<li class="active"><a href="#new-product" data-toggle="tab">New</a></li>
				<li><a href="#used-product" data-toggle="tab">Used</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="new-product">
					<form action="edu.byu.isys413.data.actions.Purchase.action" method="get">
						<fieldset>
							<legend>New</legend>
							<label for="storeproductid">Store - Quantity</label>
							<select name="storeproductid">
								<% for(StoreProduct sp : sps) { %>
								<option value="<%= sp.getId() %>"><%= sp.getStore().getLocation() + " (" + sp.getQuantityOnHand() + " Available)" %></option>
								<% }//for %>
							</select>
							<label for="quantity">Quantity</label>
							<input type="text" name="quantity" class="input-mini">
						</fieldset>
						<jsp:include page="/shipping.jsp"/>
						<fieldset>
							<button class="btn btn-primary">Purchase</button>
						</fieldset>
					</form>
				</div>
				<div class="tab-pane" id="used-product">
					<form action="edu.byu.isys413.data.actions.Purchase.action" method="get">
						<fieldset>
							<legend>Used</legend>
							<label for="forsaleid">Product - Location</label>
							<select name="forsaleid">
								<% for(ForSale fs : fss) { %>
								<option value="<%= fs.getId() %>"><%= fs.getStore().getLocation() + " (Serial: " + fs.getSerialNum() + ")" %></option>
								<% } %>
							</select>
						</fieldset>
						<jsp:include page="/shipping.jsp"/>
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
