package com.finance.form.api_param;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 通过接口创建用户
 * 
 * @author Liyixiao
 *
 */
public class RegisteApiParam extends ApiParam {
	private final static String[] fieldNames = { "system", "userid", "username", "email", "accountInitAmount",
			"scoreInitAmount" };
	
	private String system;
	private String channel;
	private String userid;
	private String username;
	private String email;
	private float accountInitAmount;
	private String accountInitReason;
	private float scoreInitAmount;
	private String scoreInitReason;
	private String operator;
	private String user_type;
	private String address;
	private String vat_no;
	private String pay_type;
	private float pay_limit;
	private Date datetime;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getAccountInitAmount() {
		return accountInitAmount;
	}

	public void setAccountInitAmount(float accountInitAmount) {
		this.accountInitAmount = accountInitAmount;
	}

	public String getAccountInitReason() {
		return accountInitReason;
	}

	public void setAccountInitReason(String accountInitReason) {
		this.accountInitReason = accountInitReason;
	}

	public float getScoreInitAmount() {
		return scoreInitAmount;
	}

	public void setScoreInitAmount(float scoreInitAmount) {
		this.scoreInitAmount = scoreInitAmount;
	}

	public String getScoreInitReason() {
		return scoreInitReason;
	}

	public void setScoreInitReason(String scoreInitReason) {
		this.scoreInitReason = scoreInitReason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVat_no() {
		return vat_no;
	}

	public void setVat_no(String vat_no) {
		this.vat_no = vat_no;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public float getPay_limit() {
		return pay_limit;
	}

	public void setPay_limit(float pay_limit) {
		this.pay_limit = pay_limit;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return checkParam(fieldNames);
	}
	
	public static void main(String args[]) throws NoSuchFieldException, SecurityException{
		Field f = RegisteApiParam.class.getDeclaredField("system");
		if(f.getType().getSimpleName().equals("float"))
			System.out.println(" == Float.class");
		else if(f.getType().getSimpleName().equals("String"))
			System.out.println(" == String.class");
		else
			System.out.println(" !- Float.class " + f.getType().getSimpleName());
	}
}
