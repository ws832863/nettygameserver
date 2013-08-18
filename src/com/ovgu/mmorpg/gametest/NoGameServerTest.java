package com.ovgu.mmorpg.gametest;

import me.prettyprint.hector.api.mutation.MutationResult;

import com.ovgu.mmorpg.cassandra.dao.GamePlayerDao;
import com.ovgu.mmorpg.cassandra.factory.GamePlayerFactory;
import com.ovgu.mmorpg.utils.ConfigurationReader;

public class NoGameServerTest {
	ConfigurationReader cReader = null;

	public NoGameServerTest() {
		cReader = ConfigurationReader.getInstance();

	}

	/**
	 * @param
	 */ 
	public void runTestCassandre(int num) {
		int executeTime = num;
		GamePlayerDao gpd = new GamePlayerDao();
		MutationResult mr;
		long mrTime = 0;
		long startTime = System.currentTimeMillis();
		while (executeTime-- > 0) {
			mr = gpd.addPlayer(GamePlayerFactory.createPlayer());
			mrTime += mr.getExecutionTimeMicro();
		}
		long endTime = System.currentTimeMillis();
		long elaspedTime = endTime - startTime;
		System.out.println("perform " + num + " times insert");
		System.out.println("endtime - starttime " + elaspedTime);
		System.out.println("cassandra execute:" + mrTime / 1000L);
	}

	public void test() {
		System.out.println("hallo world");
	}

	public static void main(String[] args) {
		NoGameServerTest nst = new NoGameServerTest();
		nst.test();
		//nst.runTestCassandre(Integer.valueOf(nst.cReader.getPersistTimePeriod()));
		// nst.runTestCassandre(1000000);

	}

}
