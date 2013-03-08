<jsp:include page="/header.jsp">
	<jsp:param name="title" value="Search Products" />
</jsp:include>

<div class="container">
	<div class="row">
		<div class="span6 offset3">
			<h1>Product Search</h1>
			<form class="form-search">
				<input type="text" class="search-query">
			</form>
			<ul class="media-list">
				<li class="media">
					<a href="product-detail.html" class="pull-left"></a>
					<div class="media-body">
						<h4>PRODUCT_NAME</h4>
						<p>PRODUCT_DESCRIPTION</p>
						<p><a href="product-detail.html" class="btn">Choose</a></p>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<script>
$(function() {
	$('.search-query').on('keyup', function() {
		var query = $('.search-query').val();
		$.ajax({
			url: '/MyStuffSprint/edu.byu.isys413.data.actions.ProductList.action?q=' + encodeURIComponent(query),
			success: function(data) {
				console.log(data);
			}
		});
	});
});
</script>
<jsp:include page="/footer.jsp" />
