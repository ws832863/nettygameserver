package com.netty.study;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

//an encoder overrides the writeRequested method to intercept a write request;

public class TimeEncoder extends SimpleChannelHandler {

	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
		UnixTime time = (UnixTime) e.getMessage();
		ChannelBuffer buf = ChannelBuffers.buffer(4);
		buf.writeInt(time.getValue());
		Channels.write(ctx, e.getFuture(), buf);
	}
}
