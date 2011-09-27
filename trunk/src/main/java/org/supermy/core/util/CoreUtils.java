package org.supermy.core.util;

import org.apache.commons.lang.StringUtils;

public class CoreUtils {
	/**
	 * 字符串转换为Long数组
	 * 
	 * @param rolesList
	 * @return
	 */
	public static Long[] String2LongArray(String rolesList,String split) {
		Long[] roles =null;
		if (StringUtils.isNotEmpty(rolesList)){
			// 将以逗号分融的角色字串解析成数组。
			String[] roles1 =  rolesList.split(split);
			roles=new Long[roles1.length];
			for (int i = 0; i < roles1.length; i++) {
				roles[i] =new Long( roles1[i]);
			}			
		}
		return roles;
	}

	public static String[] String2StringArray(String rolesList,String split) {
		//Long[] roles =null;
		if (StringUtils.isNotEmpty(rolesList)){
			// 将以逗号分融的角色字串解析成数组。
			return rolesList.split(split);
		}
		return null;
	}
	
}
