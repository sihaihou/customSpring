package com.reyco.customSpring.core.factory.alias;
/**
*@author reyco
*@date  2021年5月28日---上午10:02:44
*<pre>
*
*<pre> 
*/
public interface AliasRegistry {
	/**
	 * 注册别名
	 * @param name
	 * @param alias
	 */
	void registerAlias(String name, String alias);
	/**
	 * 移除别名
	 * @param alias
	 */
	void removeAlias(String alias);
	/**
	 * 是否有这个别名
	 * @param name
	 * @return
	 */
	boolean isAlias(String name);
	/**
	 * 获取别名
	 * @param name
	 * @return
	 */
	String[] getAliases(String name);
}
