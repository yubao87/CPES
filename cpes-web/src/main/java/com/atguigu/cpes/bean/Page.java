package com.atguigu.cpes.bean;

import java.util.List;
/**
 * ^_^ 2017年3月7日 ^_^ 下午9:00:54 ^_^ dabao ^_^
 * 
 */
public class Page<T> {
	/** 分页显示的数据 */
	private List<T> datas;
	
	/** 总的记录数 */
	private Integer totalsize;
	
	/** 总页码 */
	private Integer totalno;

	/**当前页码*/
	private Integer pageno;
	
	/**每页显示的记录数*/
	private Integer pagesize;
	
	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Integer getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(Integer totalsize) {
		this.totalsize = totalsize;
	}

	public Integer getTotalno() {
		return totalno;
	}

	public void setTotalno(Integer totalno) {
		this.totalno = totalno;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	
	

}
