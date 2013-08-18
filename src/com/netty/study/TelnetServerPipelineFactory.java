package com.netty.study;

import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;

import com.ovgu.netty.handler.MessageServerHandler;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 * 
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 * 
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 * 
 */
public class TelnetServerPipelineFactory implements ChannelPipelineFactory {

	private final ExecutionHandler executionHandler;

	public TelnetServerPipelineFactory(ExecutionHandler e) {
		this.executionHandler = e;
	}

	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = pipeline();

		// Add the text line codec combination first,
	//	pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
	//			Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("executor", executionHandler);
		// and then business logic.
		pipeline.addLast("handler", new MessageServerHandler());

		return pipeline;
	}
}
