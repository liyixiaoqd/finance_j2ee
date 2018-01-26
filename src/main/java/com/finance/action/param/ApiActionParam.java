package com.finance.action.param;

import com.alibaba.fastjson.JSONObject;
import com.finance.form.api_param.RegisteApiParam;

public class ApiActionParam {
	// 正常返回时的JSON数据
	protected JSONObject dataJson = new JSONObject();

	// 请求异常级时的错误信息
	protected String requestErrMsg;

	public JSONObject getDataJson() {
		return dataJson;
	}

	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}

	public String getRequestErrMsg() {
		return requestErrMsg;
	}

	public void setRequestErrMsg(String requestErrMsg) {
		this.requestErrMsg = requestErrMsg;
	}
	
}
