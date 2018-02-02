package com.finance.enums;


public enum CurrencyEnum {
	EUR("欧元"), GBP("英镑"),HKD("港币"),USD("美元"),RMB("人民币");

	private String desc;

	private CurrencyEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
