package com.supermy.rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.supermy.core.annotations.Comment;
import org.supermy.core.domain.BaseDomain;




@Entity
@Table
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class Parent extends BaseDomain{
	
	@Column(nullable=false)
	private String name;
	
	@Comment(value = "角色列表", desc = "一个用户具有多个角色")
	@OneToMany(cascade={javax.persistence.CascadeType.ALL},mappedBy = "parent" )//父子关系维护
	@Cascade({CascadeType.DELETE_ORPHAN})
	private Set<Child> childs=new HashSet<Child>(3);

	/**
	 * @return the childs
	 */
	public Set<Child> getChilds() {
		return childs;
	}

	/**
	 * @param childs the childs to set
	 */
	public void setChilds(Set<Child> childs) {
		this.childs = childs;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
