package com.ovgu.netty.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import network.objects.Message;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class MessageServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = Logger
			.getLogger(MessageServerHandler.class.getName());

	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	/*
	 * @Override public void handleUpstream(ChannelHandlerContext ctx,
	 * ChannelEvent e) throws Exception { if (e instanceof ChannelStateEvent) {
	 * logger.info(e.toString()); } super.handleUpstream(ctx, e); }
	 */
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) { //
		logger.log(Level.INFO, "a client is connected");
		Message msg = new Message();
		msg.setContent("you have been connected to the server");
		e.getChannel().write(msg);
	}

	/*
	 * @Override public void channelConnected(ChannelHandlerContext ctx,
	 * ChannelStateEvent e) throws Exception { // Send greeting for a new
	 * connection. e.getChannel().write( "Welcome to " +
	 * InetAddress.getLocalHost().getHostName() + "!\r\n");
	 * e.getChannel().write("It is " + new Date() + " now.\r\n"); }
	 */
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		Message msg = (Message) e.getMessage();
		// String msg = (String) e.getMessage();
		Message response = new Message();
		boolean close = false;

		if (msg == null || msg.getContent().trim().length() == 0) {
			response.setContent("Please type something");
		} else if (msg.getContent().toLowerCase().equals("exit")) {
			response.setContent("Have a good day");
			close = true;
		} else {
			UserCommandHandler.handleCommand(e.getChannel(), msg);
		}

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the
		// conversion.

		// Close the connection after sending 'Have a good day!' // if the
		// client has sent 'bye'.

		if (close) {
			ChannelFuture future = e.getChannel().write(response);
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		// System.err.println("Client has a error,Error cause:" + e.getCause());
		// e.getChannel().close();
	}
}
