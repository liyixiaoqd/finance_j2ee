package com.finance.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.finance.pojo.User;
import com.finance.pojo.UserFinanceWater;
import com.finance.service.UserFinanceWaterService;
import com.finance.util.AssembleString;

/***
 * 导出文件Action
 * @author Liyixiao
 *
 */
@Controller("exportActionBean")
@Scope("prototype")	//非单例模式
public class ExportAction {
	
    private String downloadFileName = "export"; // 返回文件名
    private transient InputStream excelStreamName = null; 

	@Autowired
	UserFinanceWaterService ufwService;
	
    private User user;
    

	public String user_water(){
		downloadFileName = "用户流水_"+user.getId()+".xls";
		
		List<UserFinanceWater> ufws = ufwService.listByUser(user, null);
		
		StringBuilder excelBuf = new StringBuilder();
		
		AssembleString.execlPojoAssemble(excelBuf, UserFinanceWater.class, ufws);
		
		String excelString = excelBuf.toString();
		try {
			excelStreamName = new ByteArrayInputStream(excelString.getBytes("GBK"), 0, excelString.length());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getReturnFileType("xls");
	}
	
	
	/**
	 * 获取返回对应的struts result名称
	 * @param type
	 * @return
	 */
	private String getReturnFileType(String type){
		if(type=="xls")
			return "excel";
		
		return null;
	}
	

	public String getDownloadFileName() throws UnsupportedEncodingException {
		return new String(downloadFileName.getBytes(),"ISO8859-1");
	}


	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}


	public InputStream getExcelStreamName() {
		return excelStreamName;
	}


	public void setExcelStreamName(InputStream excelStreamName) {
		this.excelStreamName = excelStreamName;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	
}
