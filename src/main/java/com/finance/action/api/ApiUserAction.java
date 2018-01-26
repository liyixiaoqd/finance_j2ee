package com.finance.action.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finance.action.param.ApiActionParam;
import com.finance.enums.UserEnum;
import com.finance.form.UserAddWaterForm;
import com.finance.form.api_param.ApiParam;
import com.finance.form.api_param.ModifyApiParam;
import com.finance.form.api_param.ObtainApiParam;
import com.finance.form.api_param.RegisteApiParam;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserAccountService;
import com.finance.service.UserFinanceWaterService;
import com.finance.service.UserService;
import com.finance.util.exception.ApiParamException;

/***
 * API ACTION
 * 
 * @author Liyixiao
 *
 */
@Controller("apiUserActionBeanV1")
@Scope("prototype") // 非单例模式
public class ApiUserAction extends ApiBaseAction {
	@Autowired
	UserService userService;
	@Autowired
	UserAccountService uaService;
	@Autowired
	UserFinanceWaterService ufwService;

	/**
	 * 注册用户 , 产生流水等信息表
	 * 
	 * @return
	 */
	public String registe() {
		JSONArray msgArray = new JSONArray();
		JSONArray waterNoArray = new JSONArray();

		dataJson.put("status", "failure");
		try {

			RegisteApiParam registeParam = new RegisteApiParam();
			paramToApiParam(registeParam);
			String errMsg = registeParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new ApiParamException(errMsg);
			
			dataJson.put("system", registeParam.getSystem());
			dataJson.put("channel", registeParam.getChannel());
			dataJson.put("userid", registeParam.getUserid());
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);
			
			User u = new User();
			u.setEmail(registeParam.getEmail());
			u.setOperator(registeParam.getOperator());
			u.setOperdate(registeParam.getDatetime());
			u.setSystem(registeParam.getSystem());
			u.setUserid(registeParam.getUserid());
			u.setUsername(registeParam.getUsername());

			if (userService.getByCon(u) != null) {
				throw new Exception(u.getUserid() + " has registed");
			}

			List<UserAccount> uas = new ArrayList<UserAccount>();
			UserAccount ua1 = new UserAccount();
			ua1.setReason(registeParam.getAccountInitReason());
			ua1.setType(UserEnum.UserWaterType.eCash);
			ua1.setValue(registeParam.getAccountInitAmount());

			UserAccount ua2 = new UserAccount();
			ua2.setReason(registeParam.getScoreInitReason());
			ua2.setType(UserEnum.UserWaterType.score);
			ua2.setValue(registeParam.getScoreInitAmount());

			uas.add(ua1);
			uas.add(ua2);

			List<Integer> ufwIds = userService.register(u, uas);
			for (Integer i : ufwIds) {
				waterNoArray.add(i);
			}

			dataJson.put("system", registeParam.getSystem());
			dataJson.put("channel", registeParam.getChannel());
			dataJson.put("userid", registeParam.getUserid());
			dataJson.put("status", "success");
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);
		} catch (Exception e) {
			if (e instanceof ApiParamException) {
				requestErrMsg = e.getMessage();
				return "RequestParamErr";
			}
			dataJson.put("reasons", errProc(msgArray, e.getMessage()));
		}
		return "jsonReturn";
	}

	/***
	 * 获取用户下的所有种类金额
	 * 
	 * @return
	 */
	public String obtain() {
		try {
			JSONArray type = new JSONArray();

			ObtainApiParam obtainParam = new ObtainApiParam();
			paramToApiParam(obtainParam);
			String errMsg = obtainParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new ApiParamException(errMsg);

			dataJson.put("userid", obtainParam.getUserid());
			dataJson.put("type", type);

			User user = new User();
			user.setUserid(obtainParam.getUserid());
			user.setSystem(obtainParam.getSystem());
			user = userService.getByCon(user);

			uaService.fillUser(user);

			for (UserAccount ua : user.getUserAccounts()) {
				JSONObject type_info = new JSONObject();
				type_info.put("watertype", ua.getType());
				type_info.put("amount", ua.getValue());
				type.add(type_info);
			}

			dataJson.put("type", type);
		} catch (Exception e) {
			if (e instanceof ApiParamException) {
				requestErrMsg = e.getMessage();
				return "RequestParamErr";
			}
			System.out.println("obtain exception:" + e.getMessage());
		}
		return "jsonReturn";
	}

	/***
	 * 修改用户余额
	 * 
	 * @return
	 */
	public String modify() {
		JSONArray msgArray = new JSONArray();
		JSONArray waterNoArray = new JSONArray();
		
		dataJson.put("status", "failure");
		try {
			ModifyApiParam modifyParam = new ModifyApiParam();
			paramToApiParam(modifyParam);
			String errMsg = modifyParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new ApiParamException(errMsg);

			dataJson.put("system", modifyParam.getSystem());
			dataJson.put("channel", modifyParam.getChannel());
			dataJson.put("userid", modifyParam.getUserid());
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);
			
			User user = new User();
			user.setUserid(modifyParam.getUserid());
			user.setSystem(modifyParam.getSystem());

			user = userService.getByCon(user);
			List<Integer> ufwIds = ufwService.addWatersByApi(user, modifyParam);
			for (Integer i : ufwIds) {
				waterNoArray.add(i);
			}

			dataJson.put("status", "success");
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);
		} catch (Exception e) {
			if (e instanceof ApiParamException) {
				requestErrMsg = e.getMessage();
				return "RequestParamErr";
			}
			dataJson.put("reasons", errProc(msgArray, e.getMessage()));
		}
		return "jsonReturn";
	}
	
	/**
	 * 获取流水
	 */
	public String water_obtain() {
		try {
			JSONArray waters = new JSONArray();

			ObtainApiParam obtainParam = new ObtainApiParam();
			paramToApiParam(obtainParam);
			String errMsg = obtainParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new ApiParamException(errMsg);

			dataJson.put("userid", obtainParam.getUserid());
			dataJson.put("has_next", false);
			dataJson.put("water", waters);

			User user = new User();
			user.setUserid(obtainParam.getUserid());
			user.setSystem(obtainParam.getSystem());
			user = userService.getByCon(user);
			
			boolean has_next = false;
			List<UserFinanceWater> ufws = ufwService.listByWaterNo(user, obtainParam);
			if(!ufws.isEmpty() && ufws.size() == 31){
				ufws = ufws.subList(0, 30);
				has_next = true;
			}
			
			for (UserFinanceWater ufw : ufws) {
				JSONObject water = new JSONObject();
				water.put("type", ufw.getType().toString());
				water.put("symbol", ufw.getSymbol());
				water.put("old_amount", ufw.getOld_amount());
				water.put("amount", ufw.getAmount());
				water.put("new_amount", ufw.getNew_amount());
				water.put("operdate ", ufw.getOperdate());
				water.put("water_no ", ufw.getId());
				water.put("reason ", ufw.getReason());
				
				waters.add(water);
			} 

			dataJson.put("has_next", has_next);
			dataJson.put("waters", waters);
		} catch (Exception e) {
			if (e instanceof ApiParamException) {
				requestErrMsg = e.getMessage();
				return "RequestParamErr";
			}
			System.out.println("obtain exception:" + e.getMessage());
		}
		return "jsonReturn";
	}
}
