package com.ynhuang.produce;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * �����ߴ��������߳���
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
			System.out.println("Զ�̵��ö�����Ϊ��" + service.getName());
			System.out.println("Զ�̵��÷�����Ϊ��" + methodName);
			for (Object obj : arguments) {
				System.out.println("Զ�̵��ò���Ϊ:" + obj);
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
