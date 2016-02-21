package com.nio.entity;
/** 
 * @author zoushaohua
 * @version 创建时间：2016年1月13日 下午3:23:35 
 * 类说明 
 */
public class Message {
	
	public String fromId ;
	public String toId;
	public Long time ;
	public String msgType ;
	public String content ;
	
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
