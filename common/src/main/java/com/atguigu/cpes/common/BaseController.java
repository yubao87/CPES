package com.atguigu.cpes.common;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
	// 将数据放到线程当中，数据就可以共享了
	// 与当前的线程绑定了，多线程访问就没问题了，ThreadLocal不能解决线程安全问题，解决了多例
	//result.remove();
	// 这个set方法是把执行该方法的对象作为key，
	//value是在执行该方法的线程在内存中new的一个HashMap来存储数据的
	private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

	protected void start() {
		threadLocal.set(new HashMap<String, Object>());
	}

	protected void param(String key, Object val) {
		threadLocal.get().put(key, val);
	}

	protected void success(boolean flag) {
		threadLocal.get().put("success", flag);
	}

	protected void error(String msg) {
		threadLocal.get().put("error", msg);
	}

	protected Object end() {
		return threadLocal.get();
	}

}
