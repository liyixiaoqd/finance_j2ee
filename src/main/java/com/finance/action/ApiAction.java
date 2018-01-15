package com.finance.action;

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
import com.finance.enums.UserEnum;
import com.finance.form.api_param.ApiParam;
import com.finance.form.api_param.RegisteApiParam;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.service.UserService;

/***
 * API ACTION
 * 
 * @author Liyixiao
 *
 */
@Controller("apiActionBeanV1")
@Scope("prototype") // 非单例模式
public class ApiAction {
	private JSONObject dataJson = new JSONObject();

	@Autowired
	UserService userService;

	private RegisteApiParam registeParam;

	/**
	 * 注册用户  , 产生流水等信息表
	 * @return
	 */
	public String registe() {
		JSONArray msgArray = new JSONArray();
		JSONArray waterNoArray = new JSONArray();

		try {
			dataJson.put("system", null);
			dataJson.put("channel", null);
			dataJson.put("userid", null);
			dataJson.put("status", "failure");
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);

			registeParam = new RegisteApiParam();
			paramToApiParam(registeParam);
			String errMsg = registeParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new Exception(errMsg);
			
			User u = new User();
			u.setEmail(registeParam.getEmail());
			u.setOperator(registeParam.getOperator());
			u.setOperdate(registeParam.getDatetime());
			u.setSystem(registeParam.getSystem());
			u.setUserid(registeParam.getUserid());
			u.setUsername(registeParam.getUsername());

			if(userService.getByCon(u)!=null){
				throw new Exception(u.getUserid()+" has registed");
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
			for(Integer i : ufwIds){
				waterNoArray.add(i);
			}
			
			dataJson.put("system", registeParam.getSystem());
			dataJson.put("channel", registeParam.getChannel());
			dataJson.put("userid", registeParam.getUserid());
			dataJson.put("status", "success");
			dataJson.put("reasons", msgArray);
			dataJson.put("water_no", waterNoArray);		
		} catch (Exception e) {
			dataJson.put("reasons", errProc(msgArray, e.getMessage()));
		}
		return "jsonReturn";
	}

	private void paramToApiParam(ApiParam ap){
		HttpServletRequest request = ServletActionContext.getRequest();
		ap.paramTransfer(request.getParameterMap());
	}
	
	private JSONArray errProc(JSONArray arr, String msgs) {
		if (StringUtils.isEmpty(msgs)) {
			JSONObject o = new JSONObject();
			o.put("reason", "unknown exception");
			arr.add(o);
		} else {
			for (String msg : msgs.split("\n")) {
				JSONObject o = new JSONObject();
				o.put("reason", msg);
				arr.add(o);
			}
		}
		return arr;
	}

	public JSONObject getDataJson() {
		System.out.println("get JSONObject:" + dataJson);
		return dataJson;
	}

	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RegisteApiParam getRegisteParam() {
		return registeParam;
	}

	public void setRegisteParam(RegisteApiParam registeParam) {
		this.registeParam = registeParam;
	}

}
