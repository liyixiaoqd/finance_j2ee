package com.finance.action.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finance.action.param.ApiActionParam;
import com.finance.form.api_param.ApiParam;

public class ApiBaseAction extends ApiActionParam {
	protected void paramToApiParam(ApiParam ap) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String[]> paramMap = new HashMap<String, String[]>();

		// post application/json 不自动解析,需要读取流 进行处理
		// request.getParameter()、
		// request.getInputStream()、request.getReader()这三种方法是有冲突的，因为流只能被读一次。
		if (request.getContentType().equals("application/json")) {
			try {
				StringBuilder sb = new StringBuilder();
				String line = null;

				BufferedReader bufferReader = null;
				bufferReader = request.getReader();
				while ((line = bufferReader.readLine()) != null) {
					sb.append(line);
				}

				ap.paramTransferJson(JSONObject.parseObject(sb.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ap.paramTransferMap(request.getParameterMap());
		}
	}

	protected JSONArray errProc(JSONArray arr, String msgs) {
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
}
