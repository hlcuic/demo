package com.example.demo.schedule.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class AsyncConnector{

	private Logger logger = LoggerFactory.getLogger(AsyncConnector.class);
	
	private ClientHandler clientHandler;
	
	private String host;

	private Integer port;

	public AsyncConnector(ClientHandler clientHandler, String host, Integer port) {
		this.clientHandler = clientHandler;
		this.host = host;
		this.port = port;
	}

	public void startConnector() {
		/** 配置客户端 NIO 线程组/池 */
		EventLoopGroup group = new NioEventLoopGroup();
		try {

			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);

			b.handler(getChannelHandler());
			/** connect：发起异步连接操作，调用同步方法 sync 等待连接成功 */
			ChannelFuture channelFuture = b.connect(host, port).sync();
			System.out.println(Thread.currentThread().getName() + ",客户端发起异步连接..........");

			/** 等待客户端链路关闭 */
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			/** 优雅退出，释放NIO线程组 */
			group.shutdownGracefully();
		}

	}

	public boolean send(String message)  {
		logger.info("client send message: {}",message);
		return clientHandler.send(message);
	}

	private ChannelHandler getChannelHandler() {
		return new CustomChannelInitializer();
	}

	class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new StringDecoder()).addLast(clientHandler);
		}

	};

}
