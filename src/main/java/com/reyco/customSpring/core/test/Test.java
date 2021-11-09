package com.reyco.customSpring.core.test;

import java.io.Serializable;

/**
*@author reyco
*@date  2020年11月17日---下午3:59:39
*<pre>
*
*<pre> 
*/
public class Test implements Serializable {
	private String id;
	private String name;
	public Test() {
		// TODO Auto-generated constructor stub
	}
	public Test(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + "]";
	}
	
}
