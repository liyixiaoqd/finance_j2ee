package com.finance.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.finance.util.DateUtil;

public class ApiThirdServiceActionTest {
	private final static String URL_EXCHANGE_RATE = "http://127.0.0.1:8080/finance/api/v1_exchange_rate";
	
	private CloseableHttpClient httpClient = null;
	RequestConfig requestConfig = null;

	@Before
	public void setUp() {
		System.out.println("=========== ApiUserActionTest2 setUp .. ===========");
		httpClient = HttpClients.createDefault();
		// 配置超时时间
		requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
	}

	@After
	public void tearDown() {
		System.out.println("=========== ApiUserActionTest2 tearDown .. ===========");
	}
	
	@Test
	public void testPostExchangeRate() {
		System.out.println("get exchange_rate : " + URL_EXCHANGE_RATE);
		HttpPost httpPost = new HttpPost(URL_EXCHANGE_RATE);
		httpPost.setConfig(requestConfig);
		
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("currencys",new String[]{"GBP","EUR"}); 
		jsonParam.put("rate_date", DateUtil.getStrTodayYMD());
		
		System.out.println(jsonParam.toJSONString());
		StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");// 解决中文乱码问题
		entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");
		httpPost.setEntity(entity);
		
		HttpResponse httpResponse;
		String strResult = null;
		try {
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse != null) {
				int StatusCode = httpResponse.getStatusLine().getStatusCode();
				if (StatusCode == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else {
					System.out.println("Error Response: " + httpResponse.getStatusLine().toString());
				}
			} else {
				System.out.println("httpResponse is null?");
			}

			System.out.println("strResult:" + strResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(strResult);
	}
}
