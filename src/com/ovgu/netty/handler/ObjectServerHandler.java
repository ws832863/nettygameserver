package com.ovgu.netty.handler;


import network.objects.Message;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.ovgu.mmorpg.unitest.Command;

/*
 * object transform
 */
public class ObjectServerHandler extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		System.out.println("msg received");
		Message msg = (Message) e.getMessage();
		System.out.println(msg.getContent());
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		// System.err.println("Client has a error,Error cause:" + e.getCause());
		// e.getChannel().close();
	}
}
