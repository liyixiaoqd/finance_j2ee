package com.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.UserAccountDao;
import com.finance.dao.UserDao;
import com.finance.enums.UserEnum.UserWaterType;
import com.finance.form.UserSearchForm;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.service.UserAccountService;
import com.finance.service.UserService;
import com.finance.util.AssembleString;
import com.finance.util.Page;

//required 默认
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserAccountDao userAccountDao;

	@SuppressWarnings("unchecked")
	public List<User> listUserByPage(Page page) {
		// TODO Auto-generated method stub
		List<User> users;
		if (page == null)
			users = (List<User>) userDao.listObject(false);
		else
			users = (List<User>) userDao.listObjectByPage(page, false);
		return users;
	}

	@Transactional(readOnly = false)
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		userDao.addObject(user);

		UserAccount ua = new UserAccount();
		ua.setType(UserWaterType.score);
		ua.setUser(user);
		ua.setValue(0.0f);
		userAccountDao.addObject(ua);

		ua = new UserAccount();
		ua.setType(UserWaterType.eCash);
		ua.setUser(user);
		ua.setValue(0.0f);
		userAccountDao.addObject(ua);
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return userDao.getObjectTotal();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listByForm(UserSearchForm userSearchForm, Page page) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		String sql = getSql("pojo", userSearchForm);

		List<UserAccount> userAccountL = (List<UserAccount>) userAccountDao.listByBean(sql, userSearchForm, page,
				false);
		for (UserAccount ua : userAccountL) {
			users.add(ua.getUser());
		}

		return users;
	}

	@Override
	public int getTotalForm(UserSearchForm userSearchForm) {
		// TODO Auto-generated method stub
		String sql = getSql("count", userSearchForm);

		return userAccountDao.getTotalByBean(sql, userSearchForm);
	}

	private String getSql(String type, UserSearchForm userSearchForm) {
		StringBuilder sb = new StringBuilder();
		if (type == "count")
			sb.append("select count(*) from UserAccount");
		else
			sb.append("from UserAccount");

		if(userSearchForm!=null){
			AssembleString.conSqlAssemble(sb, userSearchForm.getSystem(), "user.system = :system");
			AssembleString.conSqlAssemble(sb, userSearchForm.getUsername(), "user.username = :username");
			AssembleString.conSqlAssemble(sb, userSearchForm.getEmail(), "user.email = :email");
			if (userSearchForm.getType() != null) {
				AssembleString.conSqlAssemble(sb, "type = :type");
				AssembleString.conSqlAssemble(sb, "value >= :beg_amount");
				if (userSearchForm.getEnd_amount() > 0.0001)
					AssembleString.conSqlAssemble(sb, "value <= :end_amount");
			} else {
				AssembleString.conSqlAssemble(sb, "type = 'score'");
			}
		}

		sb.append(" order by id desc");

		System.out.println("sql [" + sb.toString() + "]");
		return sb.toString();
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return (User) userDao.getById(id, false);
	}

	@Override
	public User getByCon(User user) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("from User");
		AssembleString.conSqlAssemble(sb, user.getSystem(), "system = :system");
		AssembleString.conSqlAssemble(sb, user.getUserid(), "userid = :userid");

		return (User) userDao.getByBean(sb.toString(), user, false);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateObject(user);
	}

}
