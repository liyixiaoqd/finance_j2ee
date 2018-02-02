package com.finance.service;

import java.util.Date;
import java.util.List;

import com.finance.dao.UserFinanceWaterDao;
import com.finance.form.UserAddWaterForm;
import com.finance.form.api_param.ModifyApiParam;
import com.finance.form.api_param.ObtainApiParam;
import com.finance.pojo.User;
import com.finance.pojo.UserFinanceWater;
import com.finance.util.Page;
import com.finance.util.exception.PojoCheckException;

public interface UserFinanceWaterService {
	public List<UserFinanceWater> listByUser(User user, Page page);

	public int getTotalByUser(User user);
	
	public List<UserFinanceWater> listByWaterNo(User user, ObtainApiParam obtainParam);
	public List<UserFinanceWater> listByDate(String procDate);

	public int addWater(User user, UserAddWaterForm uawForm) throws PojoCheckException;

	public void addBase(UserFinanceWater ufw) throws PojoCheckException;

	public List<Integer> addWatersByApi(User user,ModifyApiParam modifyParam) throws PojoCheckException;
	
	public UserFinanceWaterDao getDao();
}
