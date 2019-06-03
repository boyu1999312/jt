package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//系统返回值对象
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status; //200=ok,201=lose
	private String msg;		//提示信息
	private Object data;	//后台返回的数据
	
	/**OK*/
	public static SysResult ok() {
		return new SysResult(200, null, null);
	}
	/**数据*/
	public static SysResult msg(Object data) {
		return new SysResult(200, null, data);
	}
	/**消息和数据*/
	public static SysResult msg(String msg, Object data) {
		return new SysResult(200, msg, data);
	}
	/**失败*/
	public static SysResult fail(String msg) {
		return new SysResult(201, msg, null);
	}
}

