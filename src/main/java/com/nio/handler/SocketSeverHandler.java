package com.nio.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import com.alibaba.fastjson.JSON;
import com.nio.entity.Message;
import com.nio.server.ChannelManager;
import com.nio.tools.Utils;

/**
 * @author zoushaohua
 * @version 创建时间：2016年1月13日 下午2:13:18 类说明
 */
public class SocketSeverHandler extends ChannelHandlerAdapter {

	private static final int MAX_UN_REC_PING_TIMES = 1 ;

	// 失败计数器：未收到client端发送的ping请求
	private int unRecPingTimes = 0;

	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		super.channelActive(ctx);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Channel channel = ctx.channel();

		String info = new String(msg.toString().getBytes(), "UTF-8");

		Message message = JSON.parseObject(info, Message.class);

		String fromId = message.getFromId();

		boolean isLogin = !ChannelManager.getChannelMap().containsKey(fromId);
		
		if (isLogin) {
			
			Utils.formatPrint(String.format("用户[%s]登录了!", new Object[]{fromId}));
			// 添加新通道
			ChannelManager.addChannel(fromId, channel);
		}else{
			channel.writeAndFlush(msg);
			
			channel = ChannelManager.getChannelMap().get(message.getToId());
			
			if(channel != null)
				channel.writeAndFlush(msg);
		}
		
		// 显示释放内存
		ReferenceCountUtil.release(msg);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {

		cause.printStackTrace();
		ChannelManager.removeChannel(ctx.channel());

		ctx.close();
		
		Utils.formatPrint("服务器异常退出" + cause.getMessage());
	}

	/** 心跳处理 */
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			
			if (event.state() == IdleState.ALL_IDLE) {
				if (unRecPingTimes >= MAX_UN_REC_PING_TIMES) {
					
					String userId = ChannelManager.getUserIdMap().get(ctx.channel().id().asShortText());
					
					Utils.formatPrint(String.format("用户[%s]读写超时，服务端关闭该通道！", new Object[]{userId}));
					
					ChannelManager.removeChannel(ctx.channel());
					
					// 连续超过N次未收到client的ping消息，那么关闭该通道，等待client重连
					ctx.channel().close();
				} else {
					// 失败计数器加1
					unRecPingTimes++;
				}
				
			}
		}
	}

}
