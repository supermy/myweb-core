package org.supermy.core.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @ResponseBody 按Extjs Grid需要的格式序列化数据
 * 
 */
public class ExtData<T> extends ExtResponse {

	@JsonProperty("data")
	private final List<T> data = new ArrayList<T>();

	@JsonProperty("total")
	private int total = 0;// FIXME 总记录数量

	/**
	 * Add a single Object to the data array
	 * 
	 * @param item
	 *            the Object to add to the array
	 */
	public void add(Collection<T> item) {

		if (item == null)
			return;
			for (T object : item) {
				data.add(object);
				total=total+1;
			}
	}

	public void add(T item) {
		if (item == null)
			return;
		data.add(item);
		total=total+1;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}


}
