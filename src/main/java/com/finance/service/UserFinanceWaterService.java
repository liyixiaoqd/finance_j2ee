package com.finance.service;

import java.util.List;

import com.finance.form.UserAddWaterForm;
import com.finance.pojo.User;
import com.finance.pojo.UserFinanceWater;
import com.finance.util.Page;

public interface UserFinanceWaterService {
	public List<UserFinanceWater> listByUser(User user,Page page);
	public int getTotalByUser(User user);
	
	public void addWater(User user,UserAddWaterForm uawForm);
	
	public void addBase(UserFinanceWater ufw);
}
