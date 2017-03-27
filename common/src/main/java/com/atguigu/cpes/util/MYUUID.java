package com.atguigu.cpes.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.Test;
/** 该类的返回值为30位，调用时自己加上 10用户Id 11角色Id 12组织Id 共计32位
 * 定义用户Id 格式为  10 20160916 234543 纳秒14位 随机数00
 * 2+8+6+14+2 32位  
 */
public class MYUUID {
	/** 该类的返回值为30位，调用时自己加上 10用户Id 11角色Id 12组织Id 共计32位
	 * 定义用户Id 格式为  10 20160916 234543 纳秒14位 随机数00
	 * 2+8+6+14+2 32位  
	 */
	@Test
	/**获取日期时间如：20170123 123456 */
	public static String  ID() {
		Calendar calendar = new GregorianCalendar(); // 实例化Calendar类对象
		int y= calendar.get(Calendar.YEAR);
		int m=calendar.get(Calendar.MONTH) + 1;
		int d= calendar.get(Calendar.DAY_OF_MONTH);
		int h= calendar.get(Calendar.HOUR_OF_DAY);
		int m2= calendar.get(Calendar.MINUTE);
		int s= calendar.get(Calendar.SECOND);
//		int m3= calendar.get(Calendar.MILLISECOND);
		long na=System.nanoTime();
//		System.out.println("nanoTime ："+na);
		Random random = new Random();
		int end2 = random.nextInt(9999);
		//如果不足两位前面补0 String.format("%02d", end2)
		String str=String.format("%04d",y)+String.format("%02d",m)+String.format("%02d",d)
				+String.format("%02d",h)+String.format("%02d",m2)+String.format("%02d",s)
				+String.format("%14d",na)+String.format("%02d", end2);
//		System.out.println(str);
		return str;
	}
	/**获取文件名  2017\\03\\23\\6666*/ 
	public static String IMGNAME() {
		Calendar calendar = new GregorianCalendar(); // 实例化Calendar类对象
		int y= calendar.get(Calendar.YEAR);
		int m=calendar.get(Calendar.MONTH) + 1;
		int d= calendar.get(Calendar.DAY_OF_MONTH);
		int h= calendar.get(Calendar.HOUR_OF_DAY);
		int m2= calendar.get(Calendar.MINUTE);
		int s= calendar.get(Calendar.SECOND);
//		int m3= calendar.get(Calendar.MILLISECOND);
		long na=System.nanoTime();
//		System.out.println("nanoTime ："+na);
		Random random = new Random();
		int end4 = random.nextInt(9999);
		//如果不足两位前面补0 String.format("%02d", end2)
		String str=String.format("%04d",y)+"\\"+String.format("%02d",m)+"\\"+String.format("%02d",d)+"\\"
				+"\\"+String.format("%02d", end4);
//		System.out.println(str);
		return str;
	}
	/**
	 * 测试纳秒结果为 十万个里面没有一个是重复的
	 */
	@Test
	public void Testnano() {
		for (int i = 0; i < 100000; i++) {
			long n=System.nanoTime();
			System.out.println(i+"===="+n);
		}
	}
	/**
	 * 测试毫秒结果为 一万个里面至少有十分一的重复
	 */
	@Test
	public void Testcurr() {
		for (int i = 0; i < 10000; i++) {
			long n=System.currentTimeMillis();
			System.out.println(i+"===="+n);
		}
	}
	public static void main(String[] args) {
		MYUUID id=new MYUUID();
		System.out.println("用户Id: 11"+id.ID());
		System.err.println("角色Id: 11"+id.ID());
		
		String oid="12"+id.ID();
		System.out.println("组织机构Id："+oid);
	}

}
