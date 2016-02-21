package com.nio.server;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nio.tools.Utils;

/**
 * @author zoushaohua
 * @version 创建时间：2016年1月13日 下午2:55:12 类说明
 */

public class ChannelManager {

	/** values(userId,channel) */
	private static Map<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();

	/** values(channelId,userId) */
	private static Map<String, String> userIdMap = new ConcurrentHashMap<String, String>();
	
	public static Map<String, Channel> getChannelMap(){
		return channelMap ;
	}

	public static Map<String, String> getUserIdMap(){
		return userIdMap ;
	}
	
	public static void addChannel(String userId,Channel channel) {
		
		Channel oldChannel = channelMap.get(userId);
		
		if(oldChannel != null){
			channelMap.remove(userId);
		}
		
		channelMap.put(userId, channel);
		userIdMap.put(channel.id().asShortText(),userId);
	}

	public static void removeChannel(Channel channel) {

		String channelId = channel.id().asShortText();
		String userId = userIdMap.get(channelId);

		if(!Utils.isEmpty(userId)){
			channelMap.remove(userId);
		}
		
		userIdMap.remove(channelId);
		
		channel.disconnect();
	}

}
