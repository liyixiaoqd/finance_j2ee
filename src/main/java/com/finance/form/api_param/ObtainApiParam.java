package com.finance.form.api_param;

public class ObtainApiParam extends ApiParam {
	private final static String[] fieldNames = { "system", "userid" };

	private String system;
	private String channel;
	private String userid;
	
	//流水获取使用
	private Integer water_no;
	
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

	public static String[] getFieldnames() {
		return fieldNames;
	}

	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return super.checkParam(fieldNames);
	}

	public Integer getWater_no() {
		return water_no;
	}

	public void setWater_no(Integer water_no) {
		this.water_no = water_no;
	}


	
}
