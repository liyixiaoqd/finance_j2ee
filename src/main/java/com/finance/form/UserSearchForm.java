package com.finance.form;

import com.finance.enums.UserEnum.UserWaterType;
import com.finance.util.AssembleString;

public class UserSearchForm {
	private String system;
	private String username;
	private String email;
	private UserWaterType type;
	private float beg_amount;
	private float end_amount;
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
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
	public UserWaterType getType() {
		return type;
	}
	public void setType(UserWaterType type) {
		this.type = type;
	}
	public float getBeg_amount() {
		return beg_amount;
	}
	public void setBeg_amount(float beg_amount) {
		this.beg_amount = beg_amount;
	}
	public float getEnd_amount() {
		return end_amount;
	}
	public void setEnd_amount(float end_amount) {
		this.end_amount = end_amount;
	}
	@Override
	public String toString() {
		return "UserSearchForm [system=" + system + ", username=" + username + ", email=" + email + ", type=" + type
				+ ", beg_amount=" + beg_amount + ", end_amount=" + end_amount + "]";
	}
	
	public String toPageParam(){
		StringBuilder sb = new StringBuilder();
		
		AssembleString.conPageAssemble(sb, system, "userSearchForm.system=");
		AssembleString.conPageAssemble(sb, username, "userSearchForm.username=");
		AssembleString.conPageAssemble(sb, email, "userSearchForm.email=");
		if(type!=null)
			AssembleString.conPageAssemble(sb, type.name(), "userSearchForm.type=");
		AssembleString.conPageAssemble(sb, beg_amount+"", "userSearchForm.beg_amount=");
		AssembleString.conPageAssemble(sb, end_amount+"", "userSearchForm.end_amount=");
		
		
		return sb.toString();
	}
	
}
