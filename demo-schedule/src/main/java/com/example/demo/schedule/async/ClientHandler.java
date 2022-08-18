package com.example.demo.schedule.async;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	
	private IAsyncApplication application;
	
	public ClientHandler(IAsyncApplication application) {
		this.application = application;
	}
	
	private Map<String, Channel> channelMap = new ConcurrentHashMap<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelMap.put(channel.id().asLongText(), channel);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelMap.remove(channel.id().asLongText());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		application.onRead(msg.toString());
	}
	
	public boolean send(String message) {
		for(Map.Entry<String,Channel> entry:channelMap.entrySet()) {
			Channel channel = entry.getValue();
			ByteBuf bytebuf = channel.alloc().buffer(1024);
			try {
				bytebuf.writeBytes(message.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
//				logger.error("",e);
				return false;
			}
			channel.writeAndFlush(bytebuf).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			
		}
		
		return true;
	}

}
