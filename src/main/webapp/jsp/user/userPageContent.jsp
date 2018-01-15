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

div.pageContent div.alert{
	padding: 8px 12px;
}

table.userDisplay{
	width: 100%;
}

table.userDisplay tr{
	border-bottom: 1px solid #D3D3D3;
}

table.userDisplay td, table.userDisplay th{
	padding: 10px 10px;
}

table.userSearch{
	width: 100%;
	margin: 20px 0px;
}

table.userSearch td{
	padding: 5px;
}

table.userSearch td.desc{
	width: 10%;
}

table.userSearch td:not(.desc){
	width: 20%;
}

table.userSearch td:not(.desc) select{
	height: 30px;
	width: 80%;
	padding-left: 5px;
}
table.userSearch td:not(.desc) input{
	height: 30px;
	width: 80%;
	padding-left: 8px;
}



</style>

<div class="userPageContent">
	<ol class="breadcrumb">
		<li><a href="">首页</a></li>
		<li class="active">用户明细</li>
	</ol>

	<div class="pageContent">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>提示:</strong> 请输入查询条件进行查询!
		</div>

		<form method="post">
			<table class="userSearch">
					<tr>
						<td class="desc"><span>注册系统</span></td>
						<td>
							<select name="userSearchForm.system">  
						        <c:forEach items="${systemTypeEnum}" var="enum_type">
						        	<c:if test="${userSearchForm.system==enum_type.id}">
										<option value="${enum_type.id}" selected="selected">${enum_type.value}</option>  						        	
						        	</c:if>
									<c:if test="${userSearchForm.system!=enum_type.id}">
										<option value="${enum_type.id}">${enum_type.value}</option>  						        	
						        	</c:if>
						        </c:forEach>                             
						    </select>  
						</td>
						<td class="desc"><span>用户名</span></td>
						<td><input type="text" value="${userSearchForm.username}" name="userSearchForm.username"></td>
						<td class="desc"><span>email</span></td>
						<td><input type="text" value="${userSearchForm.email}" name="userSearchForm.email"></td>
					</tr>
					
					<tr>
						<td class="desc"><span>流水类型</span></td>
						<td>
							<select name="userSearchForm.type">  
						        <c:forEach items="${waterTypeEnum}" var="enum_type">
						        	<c:if test="${userSearchForm.type==enum_type.id}">
										<option value="${enum_type.id}" selected="selected">${enum_type.value}</option>  						        	
						        	</c:if>
									<c:if test="${userSearchForm.type!=enum_type.id}">
										<option value="${enum_type.id}">${enum_type.value}</option>  						        	
						        	</c:if>
						        </c:forEach>                             
						    </select>  
						</td>
						<td class="desc"><span>最小金额</span></td>
						<td><input type="text" value="${userSearchForm.beg_amount}" name="userSearchForm.beg_amount"></td>
						<td class="desc"><span>最大金额</span></td>
						<td><input type="text" value="${userSearchForm.end_amount}" name="userSearchForm.end_amount"></td>
					</tr>
					
					<tr>
						<td colspan=6>
							<button class="btn btn-primary" type="submit">查询</button>
						</td>
					</tr>
			</table>
		</form>

		<table class="userDisplay">	
			<tr>
				<th>用户ID</th>
				<th>用户名</th>
				<th>电子现金</th>
				<th>积分</th>
				<th>流水明细</th>
				<th>支付明细</th>	
			</tr>
			
			<c:forEach items="${users }" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.eCash}</td>
					<td>${user.score}</td>
					<td>
						<a href="user_water_list.action?user.id=${user.id}">
							<span class="glyphicon glyphicon-list-alt"></span>
						</a>
					</td>
					<td><span class="glyphicon glyphicon-list-alt"></span></td>
				</tr>
			</c:forEach>
		</table>
		
		<%@include file="../pub/page.jsp"%>
	</div>
</div>