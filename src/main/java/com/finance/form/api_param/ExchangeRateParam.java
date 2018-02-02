package com.finance.form.api_param;

import java.util.List;

public class ExchangeRateParam extends ApiParam {
	private final static String[] fieldNames = { "currencys", "rate_date" };
	
	private List<String> currencys;
	private String rate_date;
	
	
	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return super.checkParam(fieldNames);
	}


	public List<String> getCurrencys() {
		return currencys;
	}


	public void setCurrencys(List<String> currencys) {
		this.currencys = currencys;
	}


	public String getRate_date() {
		return rate_date;
	}


	public void setRate_date(String rate_date) {
		this.rate_date = rate_date;
	}
	
}
