package com.finance.util;

/***
 * 分页使用
 * 
 * @author Liyixiao
 *
 */
public class Page {
	private int start; // 起始下标
	private int count; // 显示笔数
	private int total;
	private String param;

	public Page() {
		start = 0;
		count = 20;
	}

	public Page(int start, int count) {
		this.start = start;
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getTotalPage() {
		//最少一页, 防止页面报错
		if(total==0 || count==0)
			return 1;
		
		if (total % count == 0)
			return total / count;
		else
			return total / count + 1;
	}

	public boolean ishasPreviouse() {
		if (start < count)
			return false;
		else
			return true;
	}

	public boolean ishasNext() {
		if(start+count<total)
			return true;
		else
			return false;
	}
}
