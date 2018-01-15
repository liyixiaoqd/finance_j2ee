package com.finance.action.param;

import java.util.List;

import com.finance.enums.UserEnum;

public class EnumParam extends PageParam{
	private final static List<UserEnum> waterTypeEnum = UserEnum.UserWaterType.to_display();
	private final static List<UserEnum> systemTypeEnum = UserEnum.UserSystemType.to_display();
	
	public List<UserEnum> getWaterTypeEnum() {
		return waterTypeEnum;
	}
	
	public List<UserEnum> getSystemTypeEnum() {
		return systemTypeEnum;
	}
}
