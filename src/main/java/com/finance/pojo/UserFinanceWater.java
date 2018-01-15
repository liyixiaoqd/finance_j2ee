package com.finance.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.finance.enums.UserEnum;

@Entity
@Table(name = "user_finance_water")
public class UserFinanceWater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid")
	private User user;

	@Column(name = "channel", length = 20, nullable = false)
	private String channel;

	@Column(name = "amount", scale = 8, precision = 2, nullable = false)
	private float amount;
	
	@Column(name = "old_amount", scale = 8, precision = 2, nullable = false)
	private float old_amount;
	
	@Column(name = "new_amount", scale = 8, precision = 2, nullable = false)
	private float new_amount;

	@Enumerated(EnumType.STRING)
	private UserEnum.UserWaterType type;

	@Column(name = "reason", length = 100)
	private String reason;

	private Date operdate;
	private Date created_at;
	private Date updated_at;

	public String getTypeDesc(){
		return type.getDesc();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getOld_amount() {
		return old_amount;
	}

	public void setOld_amount(float old_amount) {
		this.old_amount = old_amount;
	}

	public float getNew_amount() {
		return new_amount;
	}

	public void setNew_amount(float new_amount) {
		this.new_amount = new_amount;
	}

	public UserEnum.UserWaterType getType() {
		return type;
	}

	public void setType(UserEnum.UserWaterType type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserFinanceWater [amount=" + amount + ", old_amount=" + old_amount + ", new_amount=" + new_amount
				+ ", type=" + type + "]";
	}

	
}
