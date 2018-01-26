package com.finance.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.finance.util.exception.PojoCheckException;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "system", length = 20, nullable = false)
	private String system;

	@Column(name = "userid", length = 50, nullable = false)
	private String userid;

	@Column(name = "username", length = 50, nullable = false)
	private String username;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "operator", length = 20)
	private String operator;
	
	@Column(name = "operdate")
	private Date operdate;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;
	
	//不适用延迟加载策略, 会有no session问题
	@Transient
	private List<UserAccount> userAccounts;
	
	//通过user_account注入,方便前台显示
	@Transient
	private float eCash;
	@Transient
	private float score;

	public void check() throws PojoCheckException{
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", system=" + system + ", userid=" + userid + ", username=" + username + "]";
	}

	public float geteCash() {
		return eCash;
	}

	public void seteCash(float eCash) {
		this.eCash = eCash;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}

	
}
