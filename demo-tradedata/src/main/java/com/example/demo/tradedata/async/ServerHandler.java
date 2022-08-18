package com.example.demo.tradedata.async;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	
	private Map<String,Channel> channelMap = new ConcurrentHashMap<>();
	
	private IAsyncApplication application;
	
	public ServerHandler(IAsyncApplication application) {
		this.application = application;
	}
	
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
	
	public boolean sendMsg(String message) {
		for(Map.Entry<String,Channel> entry:channelMap.entrySet()) {
			Channel channel = entry.getValue();
			if(null!=channel&&channel.isActive()) {
				ByteBuf buf = channel.alloc().buffer(1024);
				try {
					buf.writeBytes(message.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				channel.writeAndFlush(buf).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);;
			}
		}
		return true;
	}
	
}
