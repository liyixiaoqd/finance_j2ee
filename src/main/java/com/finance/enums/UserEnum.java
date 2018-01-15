package com.finance.enums;

import java.util.ArrayList;
import java.util.List;

public class UserEnum {
	private String id;
	private String value;
	
	public static enum UserWaterType{
		score("积分"), eCash("电子现金");

		private String desc;

		private UserWaterType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public static List<UserEnum> to_display(){
			List<UserEnum> ues = new ArrayList<UserEnum>();
			ues.add(new UserEnum());
			for(UserWaterType uwt: UserWaterType.values()){
				UserEnum ue = new UserEnum();
				ue.setId(uwt.name());
				ue.setValue(uwt.getDesc());
				ues.add(ue);
			}
			
			System.out.println("ENUM INIT UserWaterType");
			return ues;
		}
	}
	
	public static enum UserSystemType{
		test("测试"), mypost4u("mypost4u"), quaie("quaie");

		private String desc;

		private UserSystemType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public static List<UserEnum> to_display(){
			List<UserEnum> ues = new ArrayList<UserEnum>();
			ues.add(new UserEnum());
			for(UserSystemType ust: UserSystemType.values()){
				UserEnum ue = new UserEnum();
				ue.setId(ust.name());
				ue.setValue(ust.getDesc());
				ues.add(ue);
			}

			System.out.println("ENUM INIT UserSystemType");
			return ues;
		}
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
