package com.finance.util.exception;

public class PojoCheckException extends Exception {
	private static final long serialVersionUID = 1L;

	public PojoCheckException(){
		super("Pojo Check exceptions");
	}

	public PojoCheckException(String msg){
		super(msg);
	}
}
