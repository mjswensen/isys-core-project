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
			<ul class="media-list products">
				<li class="media">
					<div class="media-body">
						<h4 class="muted">Search for products using the field above.</h4>
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
				if(data.length > 0) {
					$('.products').html('');
					for(var each in data) {
						var $li = $('<li></li>').addClass('media')
							.append($('<div></div>').addClass('media-body')
								.append($('<h4></h4>').text(data[each].name))
								.append($('<p></p>').text(data[each].description))
								.append($('<p></p>')
									.append($('<a></a>').addClass('btn').attr('href','/MyStuffSprint/edu.byu.isys413.data.actions.ProductDetails.action?id=' + data[each].id).text('Choose'))));
						$('.products').append($li);
					}
				} else {
					$('.products').html('<li class="muted">No products match your search.</li>');
				}
			}
		});
	});
});
</script>
<jsp:include page="/footer.jsp" />
