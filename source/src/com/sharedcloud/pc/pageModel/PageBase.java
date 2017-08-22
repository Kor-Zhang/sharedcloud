package com.sharedcloud.pc.pageModel;

import java.io.Serializable;

/**
 * 基础类，接收页面基本参数
 * @author XiaoYu
 *
 */
public class PageBase implements Serializable{
	private String order;
	private String sort;
	private Integer page;
	private Integer rows;
	private Boolean moreData;
	private Integer currtRows;
	
	public Integer getCurrtRows() {
		return currtRows;
	}
	public void setCurrtRows(Integer currtRows) {
		this.currtRows = currtRows;
	}
	public Boolean getMoreData() {
		return moreData;
	}
	public void setMoreData(Boolean moreData) {
		this.moreData = moreData;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public PageBase() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageBase(String order, String sort, Integer page, Integer rows) {
		super();
		this.order = order;
		this.sort = sort;
		this.page = page;
		this.rows = rows;
	}
	
	@Override
	public String toString() {
		return "PageBase [order=" + order + ", sort=" + sort + ", page=" + page
				+ ", rows=" + rows + "]";
	}
	
}
