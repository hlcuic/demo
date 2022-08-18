package com.example.demo.tradedata.async;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class AsyncServerService{

	private Integer port;
	
	private ServerHandler serverHandler;

	public AsyncServerService(ServerHandler serverHandler,Integer port) {
		this.serverHandler = serverHandler;
		this.port = port;
	}

	public void startBind() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new StringDecoder()).addLast(serverHandler);
						};
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {

		} finally {
			// 资源优雅释放
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}
	
	public boolean send(String msg) {
		return serverHandler.sendMsg(msg);
	}


}
