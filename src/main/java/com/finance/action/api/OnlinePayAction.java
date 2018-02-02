package com.finance.action.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("apiOnlinePayActionBeanV1")
@Scope("prototype") // 非单例模式
public class OnlinePayAction extends ApiBaseAction{
	public String submit(){
		
		return "jsonReturn";
	}
}
