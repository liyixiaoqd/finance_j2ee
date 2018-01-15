<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<style>
div.userPageContent{
	overflow:hidden
}

ol.breadcrumb{
	padding: 20px;
	margin-bottom: 10px;
}

div.pageContent{
	width: 94%;
	margin: 0px auto;
}

table.userInfo th{
	padding: 10px;
	border-bottom: 1px solid #D3D3D3;
}

table.userInfo td{
	padding: 10px;
}

div.buttonDiv{
	margin: 40px 10px 10px 10px;
}

table.userDisplay, table.userInfo, div.buttonDiv{
	width: 100%;
}

table.userDisplay tr{
	border-bottom: 1px solid #D3D3D3;
}

table.userDisplay td, table.userDisplay th{
	padding: 10px 10px;
}

</style>

<script>
	$(function(){
		$("button.addUserWaterBtn").click(function(){
			$("#addUserWaterModal").modal("show");
		});
	});
</script>

<div class="userPageContent">
	<ol class="breadcrumb">
		<li><a href="">首页</a></li>
		<li><a href="user_list.action">用户明细</a></li>
		<li class="active">财务流水明细</li>
	</ol>

	<div class="pageContent">
		<table class="userInfo">
			<tr>
				<th>用户名</th>
				<th>电子现金</th>
				<th>积分</th>
			</tr>
			<tr>
				<td>${user.username }</td>
				<td>${user.eCash }</td>
				<td>${user.score }</td>
			</tr>
		</table>

		<div class="buttonDiv">
			<button class="btn btn-primary addUserWaterBtn">新增</button>
			<a href="export_user_water.action?user.id=${user.id }"><button class="btn">导出</button></a>
			<button class="btn pull-right">支付明细</button>
		</div>
		
		<table class="userDisplay">	
			<tr>
				<th>流水类型</th>
				<th>起始</th>
				<th>变化</th>
				<th>终止</th>
				<th>来源</th>
				<th>操作时间</th>	
			</tr>
			
			<c:forEach items="${userFinanceWaters }" var="ufw">
				<tr>
					<td>${ufw.typeDesc}</td>
					<td>${ufw.old_amount}</td>
					<td>${ufw.amount}</td>
					<td>${ufw.new_amount}</td>
					<td>${ufw.channel}</td>
					<td>${ufw.operdate}</td>
				</tr>
			</c:forEach>
		</table>
		
		<%@include file="../pub/page.jsp"%>
	</div>
</div>