package com.finance.form.api_param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ModifyApiParam extends ApiParam {
	private final static String[] fieldNames = { "system", "userid", "oper" };
	
	private String system;
	private String channel;
	private String userid;
	private String operator;
	private Date datetime;
	private List<Map<String, Object>> oper;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public List<Map<String, Object>> getOper() {
		return oper;
	}

	public void setOper(List<Map<String, Object>> oper) {
		this.oper = oper;
	}

	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return super.checkParam(fieldNames);
	}

	@Override
	public String toString() {
		return "ModifyApiParam [system=" + system + ", channel=" + channel + ", userid=" + userid + ", operator="
				+ operator + ", datetime=" + datetime + ", oper=" + oper + "]";
	}

	
}
