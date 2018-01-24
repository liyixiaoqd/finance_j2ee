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
import com.finance.form.api_param.ObtainApiParam;
import com.finance.form.api_param.RegisteApiParam;
import com.finance.pojo.User;
import com.finance.pojo.UserAccount;
import com.finance.service.UserAccountService;
import com.finance.service.UserService;
import com.finance.util.ApiParamException;

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
	@Autowired
	UserAccountService uaService;

	//正常返回时的JSON数据
	private RegisteApiParam registeParam;
	//请求异常级时的错误信息
	private String requestErrMsg;

	/**
	 * 注册用户 , 产生流水等信息表
	 * 
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
				throw new ApiParamException(errMsg);

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
	 * 获取用户下的所有流水金额
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
			
			User user = new User();
			user.setUserid(obtainParam.getUserid());
			user.setSystem(obtainParam.getSystem());
			user = userService.getByCon(user);
			
			uaService.fillUser(user);
			
			for(UserAccount ua:user.getUserAccounts()){
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
			System.out.println("obtain exception:"+e.getMessage());
		}
		return "jsonReturn";
	}

	private void paramToApiParam(ApiParam ap) {
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

	public String getRequestErrMsg() {
		return requestErrMsg;
	}

	public void setRequestErrMsg(String requestErrMsg) {
		this.requestErrMsg = requestErrMsg;
	}

}
