package com.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dao.UserFinanceWaterDao;
import com.finance.enums.UserEnum;
import com.finance.enums.UserEnum.UserWaterType;
import com.finance.form.UserAddWaterForm;
import com.finance.form.api_param.ModifyApiParam;
import com.finance.form.api_param.ObtainApiParam;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserAccountService;
import com.finance.service.UserFinanceWaterService;
import com.finance.util.AssembleString;
import com.finance.util.Page;
import com.finance.util.exception.PojoCheckException;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class })
@Service
public class UserFinanceWaterServiceImpl implements UserFinanceWaterService {
	@Autowired
	UserFinanceWaterDao ufwd;

	@Autowired
	UserAccountService uaService;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinanceWater> listByUser(User user, Page page) {
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
	public int addWater(User user, UserAddWaterForm uawForm) throws PojoCheckException {
		// TODO Auto-generated method stub
		UserAccount ua = uaService.getByUserAndType(user, uawForm.getType(), true);
		if (uawForm.getOperator().equals("Sub"))
			uawForm.setAmount(uawForm.getAmount() * (-1));

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

		System.out.println("ufwd.Amount:" + uawForm.getAmount());
		System.out.println("ufwd.new_Amount:" + ufw.getNew_amount());

		ufwd.addObject(ufw);

		return ufw.getId();
	}

	@Override
	public void addBase(UserFinanceWater ufw) throws PojoCheckException {
		// TODO Auto-generated method stub
		ufwd.addObject(ufw);
	}

	/**
	 * API接口中处理多条流水
	 * 
	 * @throws PojoCheckException
	 */
	@Transactional(readOnly = false)
	@Override
	public List<Integer> addWatersByApi(User user, ModifyApiParam modifyParam) throws PojoCheckException {
		// TODO Auto-generated method stub
		List<Integer> ufwIds = new ArrayList<Integer>();

		for (Map<String, Object> modify : modifyParam.getOper()) {
			UserAddWaterForm uawForm = new UserAddWaterForm();
			uawForm.setAmount(Float.parseFloat(modify.get("amount").toString()));
			System.out.println("uawForm.Amount:" + uawForm.getAmount());
			uawForm.setOperator((String) modify.get("symbol"));
			uawForm.setReason((String) modify.get("reason"));
			String wt = (String) modify.get("watertype");
			if (wt.equals(UserEnum.UserWaterType.score.toString()))
				uawForm.setType(UserEnum.UserWaterType.score);
			else
				uawForm.setType(UserEnum.UserWaterType.eCash);

			ufwIds.add(addWater(user, uawForm));
		}

		return ufwIds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinanceWater> listByWaterNo(User user, ObtainApiParam obtainParam) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("from UserFinanceWater");
		AssembleString.conSqlAssemble(sb, "uid = " + user.getId());
		if (obtainParam.getWater_no() != null)
			AssembleString.conSqlAssemble(sb, "id > " + obtainParam.getWater_no());
		System.out.println("listByWaterNo sql : " + sb.toString());

		//API每次最多返回30条, 获取31条判断是否还能继续获取
		Page page = new Page(0, 31);
		return (List<UserFinanceWater>) ufwd.listByBean(sb.toString(), null, page, false);
	}

	@Override
	public UserFinanceWaterDao getDao(){
		return ufwd;
	}

	@Override
	public List<UserFinanceWater> listByDate(String procDate) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("from UserFinanceWater");
		AssembleString.conSqlAssemble(sb, "created_at >= '" + procDate +"'");
		System.out.println("listByWaterNo sql : " + sb.toString());

		List<UserFinanceWater> ufws = (List<UserFinanceWater>) ufwd.listByBean(sb.toString(), null, null, false);
		for(UserFinanceWater ufw:ufws){
			ufw.getUser().getId();
		}
		
		return ufws;
	}
}
