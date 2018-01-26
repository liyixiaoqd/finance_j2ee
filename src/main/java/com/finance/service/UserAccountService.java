package com.finance.service;


import com.finance.enums.UserEnum;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.util.exception.PojoCheckException;

public interface UserAccountService {
	public void fillUser(User user);
	public void updateUserAccount(UserAccount userAccount) throws PojoCheckException;
	public UserAccount getByUserAndType(User user,UserEnum.UserWaterType type,boolean isLock);
	
	public void getAndUpdateTest(UserAccount userAccount) throws PojoCheckException;
}
