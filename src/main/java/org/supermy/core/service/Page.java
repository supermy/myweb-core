package org.supermy.core.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 分页参数及查询结果封装.
 * 
 * @param <T>
 */
public class Page<T> {

	// 公共变量
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	public static final int MIN_PAGESIZE = 2;
	public static final int MAX_PAGESIZE = 200;

	// 分页参数
	//protected int start = 1;
	protected int pageNo = 1;
	protected int pageSize = MIN_PAGESIZE;
	protected String orderBy = null;
	protected String order = ASC;
	protected boolean autoTotal = true;

	// 返回结果
	protected List<T> result = null;
	protected int totalCount = -1;

	// 构造函数

	public Page() {
		super();
	}

	/**
	 * 页面尺寸大小
	 * @param pageSize
	 */
	public Page(final int pageSize) {
		setPageSize(pageSize);
	}

	/**
	 * 开始记录和页面尺寸大小
	 * @param start
	 * @param pageSize
	 */
	public Page(final int start,final int pageSize) {
		//setStart(start);
		setPageNo((start/pageSize)+1);
		setPageSize(pageSize);
	}
	
	/**
	 * 页面尺寸大小；自动计算总记录数
	 * @param pageSize
	 * @param autoTotal
	 */
	public Page(final int pageSize, final boolean autoTotal) {
		setPageSize(pageSize);
		this.autoTotal = autoTotal;
	}
	
	/**
	 * 第几页、页面尺寸、记录总数
	 * @param pageNo
	 * @param pageSize
	 * @param autoTotal
	 */
	public Page(final int pageNo, final int pageSize, final int autoTotal) {
		setPageNo(pageNo);
		setPageSize(pageSize);
		setTotalCount(autoTotal);
	}

	// 查询参数函数

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量,默认为10.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,超出MIN_PAGESIZE与MAX_PAGESIZE范围时会自动调整.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < MIN_PAGESIZE) {
			this.pageSize = MIN_PAGESIZE;
		}
		if (pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段.多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 是否已设置排序字段,仅在Criterion查询时有效.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	/**
	 * 获得排序方向,默认为asc.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(order, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equalsIgnoreCase(DESC, orderStr)
					&& !StringUtils.equalsIgnoreCase(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}

		this.order = order.toLowerCase();
	}

	/**
	 * 取得分页参数的组合字符串. 将多个分页参数组合成一个字符串方便在页面上的传递,格式为pageNo|orderBy|order.
	 */
	public String getPageRequest() {
		return getPageNo() + "|" + StringUtils.defaultString(getOrderBy())
				+ "|" + getOrder();
	}

	/**
	 * 设置分页参数的组合字符串. 将多个分页参数组合成一个字符串方便在页面上的传递,格式为pageNo|orderBy|order.
	 */
	public void setPageRequest(final String pageRequest) {

		if (StringUtils.isBlank(pageRequest))
			return;

		String[] params = StringUtils.splitPreserveAllTokens(pageRequest, '|');

		if (StringUtils.isNumeric(params[0])) {
			setPageNo(Integer.valueOf(params[0]));
		}

		if (StringUtils.isNotBlank(params[1])) {
			setOrderBy(params[1]);
		}

		if (StringUtils.isNotBlank(params[2])) {
			setOrder(params[2]);
		}
	}
	
	
//
//	public int getStart() {
//		return start;
//	}
//
//	public void setStart(int start) {
//		this.start = start;
//	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数,默认为false.
	 */
	public boolean isAutoTotal() {
		return autoTotal;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoTotal = autoCount;
	}

	// 查询结果函数

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		if (result == null)
			return Collections.emptyList();
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数,默认值为-1.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数,默认值为-1.
	 */
	public int getTotalPages() {
		if (totalCount < 0)
			return -1;

		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	/**
	 * 取得反转的排序方向. 如果有以','分隔的多个排序方向,逐一进行反转.
	 */
	public String getInverseOrder() {
		String[] orders = StringUtils.split(order, ',');

		for (int i = 0; i < orders.length; i++) {
			if (DESC.equals(orders[i])) {
				orders[i] = ASC;
			} else {
				orders[i] = DESC;
			}
		}
		return StringUtils.join(orders);
	}

	/**
	 * 
	 * 构造数据导航条 <br/> |< << [] >> >| 1 2 3 4 5 P N
	 * 
	 * @param link
	 * @param pageNavSize
	 * @return
	 */
	public String  genNav(String link, String pagename, int pageNavSize) {
		int navsize = pageNavSize / 2;
		int startpage = getPageNo() - navsize;
		int endpage =  getPageNo() + navsize;
		if (startpage <= 0) {
			startpage = 1;
		}
		int totalPages = getTotalPages();
		if (endpage > totalPages) {
			endpage = totalPages;
		}

		StringBuffer pagelink = genlink(link, pagename);

		StringBuffer result = new StringBuffer("[ ");

		genCommonLink(navsize, pagelink, result);

		for (int i = startpage; i <= getPageNo() - 1; i++) {
			result.append(genPageLink(pagelink, i, String.valueOf(i))).append(
					" ");
		}

		result.append(" ").append(getPageNo()).append(" ");

		for (int i = getPageNo() + 1; i <= endpage; i++) {
			result.append(genPageLink(pagelink, i, String.valueOf(i))).append(
					" ");
		}

		result.append(" ]");

		return result.toString();
	}

	/**
	 * |< << [] >> >|
	 * 
	 * @param navsize
	 * @param pagelink
	 * @param result
	 */
	private void genCommonLink(int navsize, StringBuffer pagelink,
			StringBuffer result) {
		// |<
		if (isHasPre()) {
			result.append(genPageLink(pagelink, 1, "|<")).append(" ");
		} else {
			result.append("|<").append(" ");
		}
		// <<
		if (pageNo - navsize >= 1) {
			result.append(genPageLink(pagelink, getPageNo() - navsize, "<<"))
					.append(" ");
		} else
			result.append("<<").append(" ");

		// <
		if (isHasPre()) {
			result.append(genPageLink(pagelink, getPrePage(), "Prev")).append(" ");
		} else {
			result.append("Prev").append(" ");
		}
		// >
		if (isHasNext()) {
			result.append(genPageLink(pagelink, getNextPage(), "Next")).append(
					" ");
		} else {
			result.append("Next").append(" ");
		}
		
		
		// >>
		if (pageNo + navsize <= getTotalPages()) {
			result.append(genPageLink(pagelink, getPageNo() + navsize, ">>"))
					.append(" ");
		} else {
			result.append(">>").append(" ");
		}
		// >|
		if (isHasNext()) {
			result.append(genPageLink(pagelink, getTotalPages(), ">|")).append(
					" ");
		} else {
			result.append(">|").append(" ");
		}

		
	
	
	}

	private StringBuffer genlink(String link, String pagename) {
		StringBuffer nav = new StringBuffer(link);
		nav.append("").append(pagename).append(".orderBy=").append(orderBy==null?"":orderBy)
				.append("&").append(pagename).append(".order=").append(order)
				.append("&").append(pagename).append(".pageNo=:pageNo");

		// <a href="">text</a> ${pageuser.prePage} pageuser.nextPage
		StringBuffer pagelink = new StringBuffer("<a href=\"");
		pagelink.append(nav).append("\">:pageText</a>");
		return pagelink;
	}

	private String genPageLink(StringBuffer pagelink, int pageno,
			String pagetext) {
		String pagelinktemp = new String(pagelink);
		pagelinktemp = pagelinktemp.replaceAll(":pageNo", String
				.valueOf(pageno));
		pagelinktemp = pagelinktemp.replaceAll(":pageText", pagetext);
		return pagelinktemp;
	}
}
