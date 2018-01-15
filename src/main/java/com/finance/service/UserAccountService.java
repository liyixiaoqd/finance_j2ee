package com.finance.service;

import com.finance.enums.UserEnum;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;

public interface UserAccountService {
	public void fillUser(User user);
	public void updateUserAccount(UserAccount userAccount);
	public UserAccount getByUserAndType(User user,UserEnum.UserWaterType type,boolean isLock);
	
	public void getAndUpdateTest(UserAccount userAccount);
}
