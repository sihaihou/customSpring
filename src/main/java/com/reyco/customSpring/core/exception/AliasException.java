package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月28日---上午10:11:33
*<pre>
*别名异常
*<pre> 
*/
public class AliasException extends RuntimeException{
	public AliasException() {
		this("别名异常");
	}
	public AliasException(String mgs) {
		super(mgs);
	}
}
