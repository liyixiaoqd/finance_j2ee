package com.finance.action.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finance.dao.ExchangeRateDao;
import com.finance.form.api_param.ExchangeRateParam;
import com.finance.pojo.ExchangeRate;
import com.finance.util.DateUtil;
import com.finance.util.exception.ApiParamException;

@Controller("apiThirdServiceActionBeanV1")
@Scope("prototype") // 非单例模式
public class ApiThirdServiceAction extends ApiBaseAction {

	@Autowired
	ExchangeRateDao erDao;

	public String exchange_rate() {
		try {
			JSONArray currency_info = new JSONArray();

			ExchangeRateParam erParam = new ExchangeRateParam();
			paramToApiParam(erParam);
			String errMsg = erParam.checkParam();
			if (!StringUtils.isEmpty(errMsg))
				throw new ApiParamException(errMsg);

			dataJson.put("rate_date", erParam.getRate_date());

			for (String currency_name : erParam.getCurrencys()) {
				JSONObject cinfo = new JSONObject();
				cinfo.put("currency", currency_name);
				cinfo.put("rate", null);
				cinfo.put("rate_datetime", null);
				cinfo.put("status", "fail");
				cinfo.put("reason", "no rate get");

				List<ExchangeRate> ers = (List<ExchangeRate>) erDao.listByPairParams(null, "currency", currency_name,
						"rate_datetime", erParam.getRate_date());
				if (!ers.isEmpty()) {
					ExchangeRate er = ers.get(0);
					if (er.isSucc()) {
						cinfo.put("rate", er.getRate());
						cinfo.put("rate_datetime", DateUtil.dateToStr(er.getCreated_at()));
						cinfo.put("status", "succ");
						cinfo.put("reason", null);
					}
				}
				
				currency_info.add(cinfo);
			}

			dataJson.put("currency_info", currency_info);
		} catch (Exception e) {
			if (e instanceof ApiParamException) {
				requestErrMsg = e.getMessage();
				return "RequestParamErr";
			}
			System.out.println("exchangeRate exception:" + e.getMessage());
		}

		return "jsonReturn";
	}
}
