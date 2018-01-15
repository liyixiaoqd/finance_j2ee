package com.finance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.UserFinanceWaterDao;
import com.finance.form.UserAddWaterForm;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserAccountService;
import com.finance.service.UserFinanceWaterService;
import com.finance.util.AssembleString;
import com.finance.util.Page;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
@Service
public class UserFinanceWaterServiceImpl implements UserFinanceWaterService{
	@Autowired
	UserFinanceWaterDao ufwd;
	
	@Autowired
	UserAccountService uaService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinanceWater> listByUser(User user,Page page) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("from UserFinanceWater");
		AssembleString.conSqlAssemble(sb, "uid = " + user.getId());
		
		return (List<UserFinanceWater>) ufwd.listByBean(sb.toString(), null, page, false);
	}

	@Override
	public int getTotalByUser(User user) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("select count(*) from UserFinanceWater");
		AssembleString.conSqlAssemble(sb, "uid = " + user.getId());
		
		return ufwd.getTotalByBean(sb.toString(), null);
	}

	@Transactional(readOnly = false)
	@Override
	public void addWater(User user, UserAddWaterForm uawForm) {
		// TODO Auto-generated method stub
		UserAccount ua = uaService.getByUserAndType(user, uawForm.getType(),true);
		if(uawForm.getOperator().equals("Sub"))
			uawForm.setAmount(uawForm.getAmount()*(-1));

		UserFinanceWater ufw = new UserFinanceWater();
		ufw.setOld_amount(ua.getValue());
		
		ua.setValue(ua.getValue() + uawForm.getAmount());
		uaService.updateUserAccount(ua);
		
		ufw.setAmount(uawForm.getAmount());
		ufw.setChannel("finance_web");
		ufw.setNew_amount(ua.getValue());
		ufw.setOperdate(new Date());
		ufw.setReason(uawForm.getReason());
		ufw.setType(uawForm.getType());
		ufw.setUser(user);
		
		ufwd.addObject(ufw);
	}

}
