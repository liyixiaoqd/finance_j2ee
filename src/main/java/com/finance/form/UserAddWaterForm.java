package com.finance.form;

import com.finance.enums.UserEnum;

public class UserAddWaterForm {
	private UserEnum.UserWaterType type;
	private String operator;
	private float amount;
	private String reason;
	public UserEnum.UserWaterType getType() {
		return type;
	}
	public void setType(UserEnum.UserWaterType type) {
		this.type = type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
