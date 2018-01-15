<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<style>
div.modal-content{
	height: 400px;
	vertical-align: middle;
	padding: 30px 50px 50px 50px;
}
div.modal-content form div{
	margin: 20px;
}

div.modal-content form div span{
	display: inline-block;
	width: 16%;
}

div.modal-content form div *:not(span){
	width: 40%;
}

div.reason span{
	vertical-align: top;
}
</style>

<script>
	var score = ${user.score}
	var eCash = ${user.eCash}
	$(function(){
		$("form.addWaterForm").submit(function(){
			if(!checkEmpty("water_type","流水类型"))
				return false;
			if(!checkNumberAndZero("amount","金额"))
				return false;
						
			var operValue=$("#operator_type").val();
			if(operValue=="Sub"){
				var typeValue=$("#water_type").val();
				var amountValue=$("#amount").val();
				if( (typeValue=="score" && amountValue > score) ||
						(typeValue=="eCash" && amountValue> eCash)){
					alert("减少后金额不可小于0")
					return false;
				}
			}
			
		});
	});
</script>

<div class="modal" id="addUserWaterModal" tabindex="-1" role="dialog">
	<div class="modal-dialog loginDivInProductPageModalDiv">
		<div class="modal-content">
			<form class="addWaterForm" action="user_water_add.action" method="post">
				<div>
					<span>流水类型</span>
					<select name="uawForm.type" id="water_type">  
				        <c:forEach items="${waterTypeEnum}" var="enum_type">
							<option value="${enum_type.id}">${enum_type.value}</option>
				        </c:forEach>                             
				    </select>  
			    </div>

				<div>
					<span>操作</span>
					<select name="uawForm.operator" id="operator_type">  
						<option value="Add" selected="selected">+</option>
						<option value="Sub">-</option>
					</select>
				</div>
				
				<div>
					<span>金额</span>
					<input type="text" value=0.0 name="uawForm.amount" id="amount">
				</div>
				
				
				<div class="reason">
					<span>操作原因</span>
					<textarea name="uawForm.reason" rows=5>财务管理系统  - 手动调整</textarea>
				</div>
				
				<div>
					<input type="hidden" name="user.id" value="${user.id}">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>