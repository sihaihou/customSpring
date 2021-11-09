package com.reyco.customSpring.core.aware;

import com.reyco.customSpring.core.Environment;

/**
*@author reyco
*@date  2021年5月25日---上午9:53:33
*<pre>
*
*<pre> 
*/
public interface EnvironmentAware extends Aware {
	
	void setEnvironment(Environment environment);
	
}
