function checkEmpty(id, name) {
	var value = $("#" + id).val();
	if (value.length == 0) {
		alert(name + "不能为空")
		$("#" + id).focus();
		return false;
	}
	return true;
}

function checkNumber(id, name) {
	var value = $("#" + id).val();
	if (value.length == 0) {
		alert(name + "不能为空")
		$("#" + id).focus();
		return false;
	}

	if (isNaN(value)) {
		alert(name + "必须是数字")
		$("#" + id).focus();
		return false;
	}
	return true;
}

function checkNumberAndZero(id,name){
	if(!checkNumber(id,name))
		return false;
	var value = $("#" + id).val();
	if(value<0.001){
		alert(name + "必须大于0")
		$("#" + id).focus();
		return false;
	}
	return true;
}

function checkInt(id, name) {
	var value = $("#" + id).val();
	if (value.length == 0) {
		alert(name + "不能为空");
		$("#" + id)[0].focus();
		return false;
	}
	if (parseInt(value) != value) {
		alert(name + "必须是整数");
		$("#" + id)[0].focus();
		return false;
	}

	return true;
}

function checkDateTime(str) {
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if (r == null)
		return false;
	r[2] = r[2] - 1;
	var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
	if (d.getFullYear() != r[1])
		return false;
	if (d.getMonth() != r[2])
		return false;
	if (d.getDate() != r[3])
		return false;
	if (d.getHours() != r[4])
		return false;
	if (d.getMinutes() != r[5])
		return false;
	if (d.getSeconds() != r[6])
		return false;
	return true;
}