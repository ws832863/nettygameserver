package com.ovgu.mmorpg;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Log4JLoggerFactory;
import org.jboss.netty.util.CharsetUtil;

import com.ovgu.mmorpg.utils.ConfigurationReader;
import com.ovgu.netty.handler.MessageServerHandler;
import com.ovgu.netty.network.GameServerPipelineFactory;

public class LaunchGameServer {
	ConfigurationReader cReader = null;

	/**
	 * @param args 
	 */
	// channel group to manager all connected channel
	public static final ChannelGroup allChannels = new DefaultChannelGroup(
			"gameserver");

	public void run() {
		// TODO Auto-generated method stub
		readAllProperties();
		// start server

		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		// set executionhandler, for database access
		// see
		// http://netty.io/3.5/api/org/jboss/netty/handler/execution/ExecutionHandler.html
		//
		OrderedMemoryAwareThreadPoolExecutor e = new OrderedMemoryAwareThreadPoolExecutor(
				200, 0, 0);
		ExecutionHandler executionHandler = new ExecutionHandler(e);
		
		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		
		// set a pipeline factory
		bootstrap.setPipelineFactory(new GameServerPipelineFactory(
				executionHandler));

		// open 8000 port
		Channel channel = bootstrap.bind(new InetSocketAddress(cReader
				.getGameServerPort()));

		allChannels.add(channel);
		waitForShutdownCommand();
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		
		bootstrap.releaseExternalResources();
		executionHandler.releaseExternalResources();
		factory.releaseExternalResources();
	}

	private void waitForShutdownCommand() {
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LaunchGameServer().run();
	}

	public void initialGameEnvironment() {

	}

	public void readAllProperties() {
		cReader = ConfigurationReader.getInstance();
		// System.out.println(cReader.getPersistTimePeriod());
	}
}
