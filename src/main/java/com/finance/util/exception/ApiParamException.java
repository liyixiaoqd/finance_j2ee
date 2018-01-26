package com.finance.util.exception;

public class ApiParamException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiParamException(){
		super("Calling API parameter exceptions");
	}
	
	public ApiParamException(String msg){
		super("Calling API parameter exceptions : "+msg);
	}
	
	public static void main(String args[]){
		try{
			throw new ApiParamException("user has registed");
		} catch (Exception e){
			System.out.println(e instanceof ApiParamException);
		}
	}
}
