package com.finance.service;

import java.util.List;

import com.finance.form.UserSearchForm;
import com.finance.pojo.User;
import com.finance.util.Page;

public interface UserService {
	public User getUser(int id);
	public List<User> listUserByPage(Page page);
	
	public void add(User user);
	public int getTotal();
	public List<User> listByForm(UserSearchForm userSearchForm,Page page);
	public int getTotalForm(UserSearchForm userSearchForm);
	public User getByCon(User user);
	public void updateUser(User user);
}
