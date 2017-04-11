package com.frame.huxh.mvpdemo.utils;


public class AppConfig {
	/**
	 * 推送服务appid
	 */
	public static final String PUSH_APP_ID = "26";

	/**
	 * 是否测试代码，写测试代码时用该值来包裹，提交代码时将该值置为false。防止误将测试代码提交
	 */
	public static boolean isTest = false;

	/*
	 * 0->1：模块内容变化，多了标签属性，清除本地缓存
	 * 1->2：模块内容变化，wifi改成WLAN，清除本地缓存
	 */
	/**
	 * 应用内部版本
	 */
	public static final int VERSION = 2;

    public static final boolean SIMPLE_UPDATE_TO_STANDARD = false;
	
	/**
	 * 功能支持
	 * @author cjx
	 *
	 */
	public static class FeatureSupport {
		/**
		 * 是否输出日志到logcat
		 */
		public static boolean LOG_LOGCAT = true;
		
		/**
		 * 是否输出日志到文件中
		 */
		public static boolean LOG_FILE = true;
		
		/**
		 * 打印栈日志
		 */
		public static boolean STACK_LOG = true;
		
		/**
		 * 打印普通日志
		 */
		public static boolean NORMAL_LOG = true;
		
		/**
		 * 打印http相关日志
		 */
		public static boolean HTTP_LOG = true;
		
		/**
		 * 打印组件生命周期日志
		 */
		public static boolean COMPONENT_LIFECYCLE_LOG = true;
		
		/**
		 * 打印数据库日志
		 */
		public static boolean DB_LOG = true;
		
		/**
		 * 打印sql
		 */
		public static boolean SQL_LOG = false;
		
		/**
		 * 图片缓存日志
		 */
		public static boolean IMAGE_CACHE_LOG = false; 
		
		/**
		 * model监听器日志
		 */
		public static boolean MODEL_LISTENER_LOG = false;
		
		/**
		 * 是否支持多个广告
		 */
		public static boolean MULTI_BANNER = true;
	}
}
