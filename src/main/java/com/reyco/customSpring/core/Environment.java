package com.reyco.customSpring.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
*@author reyco
*@date  2021年5月25日---上午9:54:09
*<pre>
*
*<pre> 
*/
public class Environment {
	
	private Map<String,Object> proportiesMap = new HashMap<String, Object>();
	
	public Environment merge(Environment environment) {
		Iterator<Entry<String, Object>> entries = environment.getProportiesMap().entrySet().iterator();
		while(entries.hasNext()){
		    Entry<String, Object> entry = entries.next();
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    this.proportiesMap.put(key, value);
		}
		return this;
	}
	public Map<String, Object> getProportiesMap() {
		return proportiesMap;
	}

	public void setProportiesMap(Map<String, Object> proportiesMap) {
		this.proportiesMap = proportiesMap;
	}
}
