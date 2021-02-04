package org.yzh.web.model;


/**  
 *  该类用于封装返回前后台交互时action返回给前端的结果信息
 * @author:wangzhen
 * @version:V1.0
 * 2018年4月25日  
 */
public class ResponseModel {
	/**
	 * 接口调用成功
	 */
	private String code = "0";
	/**
	 * 状态值说明
	 */
	private String desc = "success";
	/**
	 * 返回数据
	 */
	private Object data = null;
	
	
	
	public ResponseModel() {
		super();
	}
	
	public ResponseModel(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	public ResponseModel(String code, String desc, Object data) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
	}

	public ResponseModel(String desc) {
		super();
		this.desc = desc;
	}

	public ResponseModel(Object data) {
		super();
		this.data = data;
	}



	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setCodeAndDesc(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
}
