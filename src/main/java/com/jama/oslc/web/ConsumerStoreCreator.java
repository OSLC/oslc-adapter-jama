package com.jama.oslc.web;

import java.sql.SQLException;

import org.eclipse.lyo.server.oauth.consumerstore.FileSystemConsumerStore;
import org.eclipse.lyo.server.oauth.core.consumer.ConsumerStoreException;
import org.eclipse.lyo.server.oauth.core.consumer.LyoOAuthConsumer;

public class ConsumerStoreCreator {

	public static void main(String[] args) {
		try {
			FileSystemConsumerStore fileSystemConsumerStore = new FileSystemConsumerStore("jamaOAuthStore.xml");
			LyoOAuthConsumer lyoOAuthConsumer = new LyoOAuthConsumer("testkey", "testsecret");
			lyoOAuthConsumer.setName("magicdraw");
			fileSystemConsumerStore.addConsumer(lyoOAuthConsumer);
			fileSystemConsumerStore.closeConsumerStore();
		} catch (ConsumerStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
