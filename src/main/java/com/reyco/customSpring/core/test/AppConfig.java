package com.reyco.customSpring.core.test;

import com.reyco.customSpring.core.ComponentScan;

/**
*@author reyco
*@date  2021年5月25日---上午9:25:53
*<pre>
*
*<pre> 
*/
@ComponentScan(value={"com.van.test4.core","com.van.test4.core.service","com.van.test4.core.controller"})
//@AspectJAutoProxy(proxyTargetClass=true)
public enum AppConfig {

}
