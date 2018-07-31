package com.ynhuang.produce;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RPCProducer {
	static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void exproter(String hostName, int port) throws Exception {
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(hostName, port));
		try {
			while (true) {
				System.out.println("·þÎñ¶Ë¼àÌýÖÐ...");
				executor.execute(new ProducerTask(server.accept()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.close();
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
            public void run() {
                try {
                    RPCProducer.exproter("localhost",8080);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
	}
}
