<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<style>
div.leftNav{
	background-color: black;
	color: white;
	height: 700px;
	width: 15%;
}

div.leftTitle{
	display: block;
	background-color: #696969;
	margin: 5px;
	padding: 5px;
}

div.leftTitle span{
	padding-right: 5px;
}

div.leftSubTitle{
	margin: 5px;
	padding: 8px;
}

div.leftSubTitle span{
	display: block;
	margin-bottom: 5px;
}

div.leftNav a{
	color: white;
}
</style>

<script>
$(function(){
	$("div.leftTitle").click(function(){
		var link = $(this).attr("link");
		
		$("div.leftSubTitle[link="+link+"]").toggle();
	});
});
</script>

<div class="leftNav pull-left">
	<div class="userManage">
		<div class="leftTitle" link="user">
			<span class="glyphicon glyphicon-user"></span>
			<span>用户管理</span>
		</div>
		<div class="leftSubTitle" link="user">
			<span><a href="user_list.action">用户明细</a></span>
		</div>
	</div>
	<div class="financeManage">
		<div class="leftTitle" link="finance">
			<span class="glyphicon glyphicon-th-list"></span>
			<span>财务管理</span>
		</div>
		
		<div class="leftSubTitle" link="finance">
			<span>交易查询</span>
			<span>交易汇总</span>
		</div>
	</div>
	<div class="fileManage">
		<div class="leftTitle" link="file">
			<span class="glyphicon glyphicon-file"></span>
			<span>文件管理</span>
		</div>
		
		<div class="leftSubTitle" link="file">
			<span>文件上传</span>
		</div>
	</div>
</div>