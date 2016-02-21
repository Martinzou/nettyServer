package com.nio.entity;

/**
 * @author zoushaohua
 * @version 创建时间：2016年1月23日 上午9:20:54 类说明 消息类型
 */
public enum MsgType {

	LOGIN(1, "登录"), 
	CHAT(2, "单聊"), 
	GROUP_CHAT(3, "群聊"), 
	BROADCAST(4, "广播");

	private int type;
	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private MsgType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
}
