package com.ovgu.netty.handler;

import java.util.Hashtable;

import me.prettyprint.hector.api.mutation.MutationResult;
import network.objects.Message;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import com.ovgu.mmorpg.cassandra.dao.GamePlayerDao;
import com.ovgu.mmorpg.cassandra.factory.GamePlayerFactory;
import com.ovgu.mmorpg.gamestate.GamePlayer;

public class UserCommandHandler {

	private static Hashtable<String, Long> executeTime = new Hashtable<String, Long>();

	// static HashTable<Channel> v = new HashTable();
	static final Logger logger = Logger.getLogger(UserCommandHandler.class
			.getName());

	public static void handleCommand(Channel ch, Message msg) {
		MutationResult mr;
		GamePlayerDao gpd = new GamePlayerDao();
		if (msg.getMsgType() == 1) {
			System.out.println("user send " + msg.getContent() + " "
					+ msg.getMsgType());

			if (msg.getContent().toLowerCase().equals("add")) {
				// logger.info("adding a new player to cassadra");
				GamePlayer gp = GamePlayerFactory.createPlayer();
				// System.out.println("Add a player " + gp);
				mr = gpd.addPlayer(gp);
				String time = String.valueOf(mr.getExecutionTimeMicro());

				// executeTime.get(msg.getSenderID())==null?executeTime.put(msg.getSenderID(),
				// time.toString()):executeTime.put(msg.getSenderID(),
				// (Long.valueOf(executeTime.get(msg.getSenderID()))+time).toString())
				// System.out.println("add a player " + time);
				// Message re = new Message();
				// re.setContent("time@" + time);
				// ch.write(re);
				logger.info("senderid " + msg.getSenderID() + " executetime "
						+ time);

			}
		}

		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
