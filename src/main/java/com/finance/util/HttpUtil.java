package com.finance.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	public static final String TYPE_JSON = "json";
	public static final String TYPE_FORM = "form";

	/**
	 * 获取网页内容
	 * 
	 * @param url
	 *            -- 网页地址
	 * @param param_type
	 *            -- 参数类型 json/form
	 * @param params
	 *            -- 参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getUrlBodyByPost(String url, String param_type, JSONObject params)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 配置超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);

		if (param_type.equals("json")) {
			StringEntity entity = new StringEntity(params.toJSONString(), "utf-8");// 解决中文乱码问题
			entity.setContentType("application/json");
			entity.setContentEncoding("UTF-8");
			httpPost.setEntity(entity);
		} else {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Entry<String, Object> m : params.entrySet()) {
				nvps.add(new BasicNameValuePair(m.getKey(), (String) m.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		}

		HttpResponse httpResponse = httpClient.execute(httpPost);

		if (httpResponse == null)
			throw new RuntimeException("no info get");
		else {
			if (httpResponse.getStatusLine().getStatusCode() != 200)
				throw new RuntimeException("http return failure:" + httpResponse.getStatusLine().getStatusCode());
		}

		return EntityUtils.toString(httpResponse.getEntity());
	}

	/***
	 * 根据正则表达式返回内容
	 * 
	 * @param body
	 * @param partten
	 * @return
	 */
	public static String getContent(String body, String strPattern) {
		String content = null;
		Matcher mat = getContextMatcher(body, strPattern);

		if (mat.find()) {
			content = mat.group(1);
		}

		return content;
	}

	public static Matcher getContextMatcher(String body, String strPattern) {
		//System.out.println("pattern:" + strPattern);
		Pattern pattern = Pattern.compile(strPattern, Pattern.DOTALL);
		return pattern.matcher(body);
	}
	
	public static int getTableLineCount(String table_context){
		int line_count=0;
		Matcher mat = getContextMatcher(table_context, "<tr>(.*?)</tr>");
		while(mat.find()){
			line_count++;
		}
		
		return line_count;
	}

	/***
	 * 返回table中 第几行 第几列中的内容
	 * 
	 * @param table_context
	 * @param tr_num
	 *            -- 从0开始
	 * @param td_num
	 *            -- 从0开始
	 * @return
	 */
	public static String getContextInTable(String table_context, int tr_num, int td_num) {
		String context=null;
		Matcher mat = getContextMatcher(table_context, "<tr>(.*?)</tr>");

		// 正则捕获数据,需要先调用find方法
		int index = 0;
		while (mat.find()) {
			if (index < tr_num){
				index++;
				continue;
			}

			//获取td
			int index2=0;
			//匹配 td/th 两种情况 (?:d|h) 非捕获性匹配
			Matcher mat2 = getContextMatcher(mat.group(1), "<t(?:d|h).*?>(.*?)</t(?:d|h)>");
			while(mat2.find()){
				if (index2 < td_num){
					index2++;
					continue;
				}
				context = mat2.group(1);
				break;
			}
			break;
		}

		return context;
	}

	public static void main(String args[]) {
		String body = "<table><tr>abc</tr> <tr>bbc</tr></table>";
		getContextInTable(body, 1, 1);

		// Pattern pattern = Pattern.compile("(abc)", Pattern.DOTALL);
		// Matcher mat = pattern.matcher(body);
		// if(mat.find()){
		// System.out.println(mat.group(1));
		// }
		// else
		// System.out.println("no found");
	}
}
