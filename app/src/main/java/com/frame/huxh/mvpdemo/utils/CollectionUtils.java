package com.frame.huxh.mvpdemo.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 容器类Utils
 * @author cjx
 *
 */
public class CollectionUtils {
	private CollectionUtils() {
		
	}
	
	/**
	 * 一个容器的元素数量是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * 一个map的元素数量是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map) {
		return map == null || map.isEmpty();
	}
	
	/**
	 * 一个数组的元素数量是否为空
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 容器元素复制，使用该方法的容器类必须具体公有默认构造方法
	 * @param collection 源容器
	 * @return
	 */
	public static <T> Collection<T> clone(Collection<T> collection) {
		return clone(collection, null);
	}
	
	/**
	 * 容器元素复制
	 * @param collection 源容器
	 * @param dest 目标容器
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> clone(Collection<T> collection, Collection<T> dest) {
		if(isEmpty(collection)) {
			return null;
		}
		
		try {
			if(dest == null) {
				dest = (Collection<T>)collection.getClass().newInstance();
			}
			for(T item : collection) {
				dest.add(item);
			}
			return dest;
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * 容器元素复制，复制时加同步锁，使用该方法的容器类必须具体公有默认构造方法
	 * @param collection 源容器
	 * @param mutex 互斥对象
	 * @return
	 */
	public static <T> Collection<T> cloneSync(Collection<T> collection, Object mutex) {
		synchronized (mutex) {
			return clone(collection);
		}
	}
	
	/**
	 * 容器元素复制，复制时加同步锁
	 * @param collection 源容器
	 * @param dest 目标容器
	 * @param mutex 互斥对象
	 * @return
	 */
	public static <T> Collection<T> cloneSync(Collection<T> collection, Collection<T> dest, Object mutex) {
		synchronized (mutex) {
			return clone(collection, dest);
		}
	}
	
	/**
	 * 获取容器元素数量
	 * @param collection
	 * @return
	 */
	public static int getSize(Collection<?> collection) {
		return collection == null ? 0 : collection.size();
	}
	
	/**
	 * 将一个容器元素全部添加到另一个容器中
	 * @param dest 目标容器
	 * @param src 源容器
	 * @return
	 */
	public static <T> Collection<T> addAll(Collection<T> dest, Collection<? extends T> src) {
		if(dest == null || isEmpty(src)) {
			return dest;
		} else {
			dest.addAll(src);
			return dest;
		}
	}
	
	/**
	 * 用一个容器元素填充另一个容器
	 * @param dest 目标容器
	 * @param src 源容器
	 * @return
	 */
	public static <T> Collection<T> fill(Collection<T> dest, Collection<? extends T> src) {
		if(dest == null) {
			return null;
		} else {
			dest.clear();
			addAll(dest, src);
			return dest;
		}
	} 
	
	/**
	 * map元素复制，使用该方法的map类必须具体公有默认构造方法
	 * @param map 源map
	 * @return
	 */
	public static <K,V> Map<K,V> clone(Map<K,V> map) {
		return clone(map, null);
	}
	
	/**
	 * map元素复制
	 * @param map 源map
	 * @param dest 目标map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> clone(Map<K,V> map, Map<K,V> dest) {
		if(isEmpty(map)) {
			return null;
		}
		
		try {
			if(dest == null) {
				dest = (Map<K,V>)map.getClass().newInstance();
			}
			Set<Entry<K, V>> entrySet = map.entrySet();
			for(Entry<K, V> entry : entrySet) {
				dest.put(entry.getKey(), entry.getValue());
			}
			return dest;
		} catch (Exception e) {
			return null;
		} 
	}

	/**
	 * map元素复制，复制时加同步锁
	 * @param map 源map
	 * @param dest 目标map
	 * @param mutex 互斥对象
	 * @return
	 */
	public static <K,V> Map<K,V> cloneSync(Map<K,V> map, Map<K,V> dest, Object mutex) {
		synchronized (mutex) {
			return clone(map, dest);
		}
	}

	/**
	 * map键复制
	 * @param map 源map
	 * @return
	 */
	public static <K,V> Set<K> cloneKeys(Map<K,V> map) {
		if(isEmpty(map)) {
			return null;
		}
		
		try {
			Set<K> dest = new HashSet<K>();
			Set<Entry<K, V>> entrySet = map.entrySet();
			for(Entry<K, V> entry : entrySet) {
				dest.add(entry.getKey());
			}
			return dest;
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * Long型容器转long数组
	 * @param collection Long型容器
	 * @return
	 */
	public static long[] toLongArray(Collection<Long> collection) {
		if(isEmpty(collection)) {
			return null;
		}
		
		try {
			long[] dest = new long[collection.size()];
			int i = 0;
			for(Long item : collection) {
				dest[i++] = item;
			}
			return dest;
		} catch (Exception e) {
			return null;
		} 
	}
	
	/**
	 * 数组转容器
	 * @param array 源数组
	 * @param dest 目标容器
	 * @return
	 */
	public static <T> Collection<T> toCollection(T[] array, Collection<T> dest) {
		if(isEmpty(array) || dest == null) {
			return null;
		}
		for(T item : array) {
			dest.add(item);
		}
		return dest;
	}
	
	/**
	 * 移除列表元素
	 * @param list 目标列表
	 * @param index 即将移除的元素索引值
	 * @return
	 */
	public static <T> T remove(List<T> list, int index) {
		if(getSize(list) > index) {
			return list.remove(index);
		} else {
			return null;
		}
	}
	
	/**
	 * 为容器批量添加元素
	 * @param collection 目标容器
	 * @param items 批量元素
	 * @return
	 */
	public static <T> Collection<T> add(Collection<T> collection, T... items) {
		if(collection == null || isEmpty(items)) {
			return collection;
		} else {
			for(T item : items) {
				collection.add(item);
			}
			return collection;
		}
	}
	
	/**
	 * 将一个元素从数组中删除并返回转换后的列表
	 * @param src 目标数组
	 * @param remove 即将删除的元素
	 * @return
	 */
	public static <T> List<T> remove(T[] src, T remove) {
		if(isEmpty(src) || src.length == 1 && src[0].equals(remove)) {
			return null;
		} else {
			List<T> dest = new LinkedList<T>();
			for(T item : src) {
				if(!item.equals(remove)) {
					dest.add(item);
				}
			}
			return dest;
		}
	}
	
	/**
	 * 获取容器第一项
	 * @param collection 容器
	 * @return
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if(isEmpty(collection)) {
			return null;
		}
		Iterator<T> iterator = collection.iterator();
		return iterator.next();
	}
}
