package com.finance.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.finance.action.param.EnumParam;
import com.finance.form.UserAddWaterForm;
import com.finance.form.UserSearchForm;
import com.finance.pojo.User;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserAccountService;
import com.finance.service.UserFinanceWaterService;
import com.finance.service.UserService;
import com.finance.util.Page;
import com.finance.util.exception.PojoCheckException;

/***
 * 用户相关ACTION -- 账户,流水等
 * @author Liyixiao
 *
 */
@Controller("userActionBean")
@Scope("prototype")	//非单例模式
public class UserAction extends EnumParam{
	@Autowired
	UserService userService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserFinanceWaterService ufwService;
	
	UserSearchForm userSearchForm;
	List<User> users;
	User user;
	
	UserAddWaterForm uawForm;
	List<UserFinanceWater> userFinanceWaters;
	UserFinanceWater userFinanceWater;
	
	
	/**
	 * 用户明细页面 -- 查询
	 * @return
	 */
	public String list(){
		initUserAction();
		
		if(userSearchForm==null){
			users = userService.listUserByPage(page);
			page.setTotal(userService.getTotal());
		}
		else{
			System.out.println(userSearchForm);
			users = userService.listByForm(userSearchForm, page);
			page.setTotal(userService.getTotalForm(userSearchForm));
			page.setParam(userSearchForm.toPageParam());
		}
		
		for(User u : users){
			userAccountService.fillUser(u);
		}
		
		return "listUser";
	}

	/**
	 * 用户财务流水查询界面
	 * @return
	 */
	public String water_list(){
		initUserAction();
		
		userFinanceWaters = ufwService.listByUser(user, page);
		System.out.println("userFinanceWaters:" + userFinanceWaters.size());
		user = userService.getUser(user.getId());
		page.setTotal(ufwService.getTotalByUser(user));
		userAccountService.fillUser(user);
		
		return "listUserWater";
	}
	
	public String water_add(){
		try {
			ufwService.addWater(user, uawForm);
		} catch (PojoCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listUserWaterRedirect";
	}
	
	/**
	 * 部分初始化功能
	 * @return
	 */
	private void initUserAction(){
		if(page==null){
			page = new Page();
			System.out.println("initUserAction: page");
		}
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserSearchForm getUserSearchForm() {
		return userSearchForm;
	}

	public void setUserSearchForm(UserSearchForm userSearchForm) {
		this.userSearchForm = userSearchForm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<UserFinanceWater> getUserFinanceWaters() {
		return userFinanceWaters;
	}


	public void setUserFinanceWaters(List<UserFinanceWater> userFinanceWaters) {
		this.userFinanceWaters = userFinanceWaters;
	}


	public UserFinanceWater getUserFinanceWater() {
		return userFinanceWater;
	}


	public void setUserFinanceWater(UserFinanceWater userFinanceWater) {
		this.userFinanceWater = userFinanceWater;
	}

	public UserAddWaterForm getUawForm() {
		return uawForm;
	}

	public void setUawForm(UserAddWaterForm uawForm) {
		this.uawForm = uawForm;
	}
	
	
}
