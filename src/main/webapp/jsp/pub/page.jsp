<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<style>
nav.page{
	text-align: center;
}
</style>

<script>
$(function(){
	$("li.disabled a").click(function(){
		return false;
	});
	$("li a.current").click(function(){
		return false;
	});
});
</script>

<nav class="page">

	<ul class="pagination">
		<li <c:if test="${!page.hasPreviouse}"> class="disabled"</c:if>>
			<a href="?page.start=0&${page.param}">«</a>
		</li>
		<li <c:if test="${!page.hasPreviouse}"> class="disabled"</c:if>>
			<a href="?page.start=${page.start-page.count}&${page.param}">‹</a>
		</li>
		<c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
			<li>
				<a href="?page.start=${status.index*page.count}&${page.param}"
					<c:if test="${status.index*page.count==page.start}"> 
						class="current"
					</c:if>
				>${status.count}</a>
			</li>
		</c:forEach>
		<li <c:if test="${!page.hasNext}"> class="disabled"</c:if>>
			<a href="?page.start=${page.start+page.count}&${page.param}">›</a>
		</li>
		<li <c:if test="${!page.hasNext}"> class="disabled"</c:if>>
			<a href="?page.start=${(page.totalPage-1)*page.count}&${page.param}">»</a>
		</li>
	</ul>
</nav>