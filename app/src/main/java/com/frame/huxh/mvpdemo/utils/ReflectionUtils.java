package com.frame.huxh.mvpdemo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectionUtils {
	/**
	 * 获取类所有字段，包括其父类的字段
	 * @param destClass 目标类
	 * @param endClass 截止的父类，字段只会往上回溯到该类，再往上的类的字段不会获取
	 * @return
	 */
	public static Field[] getAllFields(Class<?> destClass, Class<?> endClass) {
		return getAllFields(destClass, endClass, false);
	}

    /**
     * 获取所有的方法类
     * @param destClass
     * @return
     */
    public static Method[] getAllMethods(Class<?> destClass) {
        Class<?> clazz = destClass;
        Method[] methods = clazz.getDeclaredMethods();
        return methods;
    }
	
	/**
	 * 获取类所有字段，包括其父类的字段
	 * @param destClass 目标类
	 * @param endClass 截止的父类，字段只会往上回溯到该类并且不会获取该类的字段
	 * @return
	 */
	public static Field[] getAllFieldsIgnore(Class<?> destClass, Class<?> endClass) {
		return getAllFields(destClass, endClass, true);
	}
	
	/**
	 * 获取所有字段，包括其父类的字段
	 * @param destClass 目标类
	 * @return
	 */
	public static Field[] getAllFields(Class<?> destClass) {
		return getAllFieldsIgnore(destClass, Object.class);
	}
	
	private static Field[] getAllFields(Class<?> destClass, Class<?> endClass, boolean ignore) {
		ArrayList<Field> destFields = new ArrayList<Field>();
		Class<?> clazz = destClass;
		while(clazz != null) {
			if(ignore && clazz == endClass) {
				break;
			}
			
			Field[] fields = clazz.getDeclaredFields();
    		if(fields != null && fields.length > 0) {
    			for(Field field : fields) {
    				destFields.add(field);
    			}
    		}
    		
    		if(!ignore && clazz == endClass) {
    			break;
    		}
    		
    		clazz = clazz.getSuperclass();
		}
		
		if(destFields.size() == 0) {
			return null;
		} else {
			return destFields.toArray(new Field[destFields.size()]);
		}
	}
	
	public static void reset(Object dest, Object src) {
		Field[] fields = getAllFieldsIgnore(dest.getClass(), Object.class);
    	if(!CollectionUtils.isEmpty(fields)) {
    		for(Field field : fields) {
    			field.setAccessible(true);
    			try {
					field.set(dest, field.get(src));
				} catch (Exception e) {
				} 
    		}
    	}
	}
	
	public static void setValue(Object host, String field, Object newValue) {
		try {
			Field destField = host.getClass().getDeclaredField(field);
			destField.setAccessible(true);
			destField.set(host, newValue);
		} catch (Exception e) {
		}
	}
}
