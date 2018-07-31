package com.ynhuang.customer;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

import com.ynhuang.produce.interfaces.People;
import com.ynhuang.produce.interfacesImpl.Man;

public class RPCConsumer<T> {

	public T improter(Class<?> serviceClass, InetSocketAddress address) {
		ConsumerHandler consumerHandler = new ConsumerHandler(serviceClass, address);
		Class<?> intf = serviceClass.getInterfaces()[0];
		Class<?>[] interfaces = new Class<?>[] { intf };
		return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), interfaces, consumerHandler);
	}

	public static void main(String[] args) {
		RPCConsumer<People> peopleRPCConsumer = new RPCConsumer<People>();
		People people = peopleRPCConsumer.improter(Man.class, new InetSocketAddress("localhost", 8080));
		System.out.println(people.sayHello("zhaochao"));
	}
}
