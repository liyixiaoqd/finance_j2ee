package com.finance.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.finance.enums.CurrencyEnum;
import com.finance.util.exception.PojoCheckException;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
	public static final int INIT = 0;
	public static final int FAIL = 1;
	public static final int SUCC_WARN = 8;
	public static final int SUCC = 9;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	private CurrencyEnum currency;

	@Column(name = "value", scale = 10, precision = 4, columnDefinition = "FLOAT default 0.0", nullable = false)
	private float rate;

	private Date rate_datetime;

	private int flag;
	private Date created_at;
	private Date updated_at;

	public void check() throws PojoCheckException{
		if(rate<0.01)
			throw new PojoCheckException("利息不可小于等于0");
	}
	
	public boolean isSucc(){
		return flag==8 || flag==9;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Date getRate_datetime() {
		return rate_datetime;
	}

	public void setRate_datetime(Date rate_datetime) {
		this.rate_datetime = rate_datetime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

}
