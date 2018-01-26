package com.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.UserAccountDao;
import com.finance.enums.UserEnum.UserWaterType;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.service.UserAccountService;
import com.finance.util.AssembleString;
import com.finance.util.exception.PojoCheckException;

//required 默认
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
@Service
public class UserAccountServiceImpl implements UserAccountService {
	@Autowired
	UserAccountDao userAccountDao;

	@SuppressWarnings("unchecked")
	@Override
	public void fillUser(User user) {
		// TODO Auto-generated method stub
		List<UserAccount> userAccountL = (List<UserAccount>) userAccountDao
				.listByBean("from UserAccount where user=" + user.getId(), null, null, false);
		user.setUserAccounts(userAccountL);
		for (UserAccount ua : userAccountL) {
			if (ua.getType() == UserWaterType.eCash)
				user.seteCash(ua.getValue());
			else if (ua.getType() == UserWaterType.score)
				user.setScore(ua.getValue());
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void updateUserAccount(UserAccount userAccount) throws PojoCheckException {
		// TODO Auto-generated method stub
		userAccountDao.updateObject(userAccount);
	}

	@Override
	public UserAccount getByUserAndType(User user, UserWaterType type,boolean isLock) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("from UserAccount");
		AssembleString.conSqlAssemble(sb, "uid = " + user.getId());
		AssembleString.conSqlAssemble(sb, "type = '" + type.name() + "'");

		return (UserAccount) userAccountDao.getByBean(sb.toString(), null, isLock);
	}

	@Transactional(readOnly = false)
	@Override
	public void getAndUpdateTest(UserAccount userAccount) throws PojoCheckException {
		// TODO Auto-generated method stub
		userAccount = (UserAccount) userAccountDao.getById(userAccount.getId(), true);
		System.out.println(userAccount.getValue());
		userAccount.setValue(userAccount.getValue() + 50);
		userAccountDao.updateObject(userAccount);
	}

}
