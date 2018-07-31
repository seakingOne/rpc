package com.ynhuang.customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * �����߶�̬������
 * 
 * @author 018399
 *
 */
public class ConsumerHandler implements InvocationHandler {

	private Class<?> object;
	private InetSocketAddress address;

	public ConsumerHandler(Class<?> object, InetSocketAddress address) {
		this.object = object;
		this.address = address;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(" call peopleProxyHandler invoke ");
		System.out.println("���ö���Ϊ��" + object.getClass().getName());
		System.out.println("���÷���Ϊ��" + method.getName());
		for (Object obj : args) {
			System.out.println("����Ϊ:" + obj.toString());
		}

		Class cl = Class.forName(object.getClass().getName());

		Socket socket = null;
		ObjectOutputStream output = null;
		ObjectInputStream input = null;

		socket = new Socket();
		socket.connect(address);
		output = new ObjectOutputStream(socket.getOutputStream());
		output.writeUTF(object.getName());
		output.writeUTF(method.getName());
		output.writeObject(method.getParameterTypes());
		output.writeObject(args);
		input = new ObjectInputStream(socket.getInputStream());

		return input.readObject();
	}

}
