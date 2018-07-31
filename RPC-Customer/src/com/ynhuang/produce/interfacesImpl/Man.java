package com.ynhuang.produce.interfacesImpl;

import com.ynhuang.produce.interfaces.People;

public class Man implements People {

	@Override
	public String sayHello(String name) {
		System.out.println("call Man sayHello ");
		return "Hello " + name;
	}

}
