package com.ynhuang.produce;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 生产者处理连接线程类
 * 
 * @author 018399
 *
 */
public class ProducerTask implements Runnable {

	Socket client = null;

	public ProducerTask(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			input = new ObjectInputStream(client.getInputStream());
			String interFaceName = input.readUTF();
			Class<?> service = Class.forName(interFaceName);
			String methodName = input.readUTF();
			Class<?>[] paramterTypes = (Class<?>[]) input.readObject();
			Object[] arguments = (Object[]) input.readObject();
			Method method = service.getMethod(methodName, paramterTypes);
			Object result = method.invoke(service.newInstance(), arguments);
			System.out.println("远程调用对象名为：" + service.getName());
			System.out.println("远程调用方法名为：" + methodName);
			for (Object obj : arguments) {
				System.out.println("远程调用参数为:" + obj);
			}
			output = new ObjectOutputStream(client.getOutputStream());
			output.writeObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
