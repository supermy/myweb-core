package org.supermy.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.supermy.core.annotations.Comment;
import org.supermy.core.web.ExtDateDeserializer;
import org.supermy.core.web.ExtDateSerializer;



/**
 * 减少属性的使用，提高效率。
 * 
 * @author my
 *
 */
@JsonAutoDetect
@MappedSuperclass
public class BaseDomain implements Serializable{

	private static final long serialVersionUID = -2023885064840350673L;

	public String toString() {
		 //开发模式
		 return ToStringBuilder.reflectionToString(this);
		// 生产模式
//		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
//				this.id).toString();
	}

	public boolean equals(BaseDomain o) {
		if (this == o) {
			return true;
		}
		// if (o.getClass().getName().equals(getClass().getName())) {// FIXME
		// return false;
		// }
		return o.getPkId().equals(pkId);
	}

	public int hashCode() {
		return (pkId != null ? pkId.hashCode() : 0);
	}
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	//oracle
//	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="fastweb_seq")    
//	//@SequenceGenerator(name="fastweb_seq",allocationSize=50, sequenceName="seq_fastweb")  	
//	//mysql user  @GeneratedValue  is ok
//	
	@Comment("物理主键")
	@Id
	@GeneratedValue
	@Column
	private Long pkId;// =new Long(0);

	//FIXME 通过类型区分不同的domain;重复的ID索引会被覆盖，对于数字型ID不是通用的解决办法；
	@Transient
	private String indexType;
	
	
	public String getIndexType() {
		this.indexType =getClass().getSimpleName();
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType =getClass().getSimpleName();
	}

//	/**
//	 * @return
//	 * 
//	 * 新的对象
//	 */
//	public boolean isnew(){
//		return id==null;
//	}

//	 @Version
//	 @Column(name = "OPTLOCK")
//	 private Integer version;	
//	 /**
//	 * @return the version
//	 */
//	 public Integer getVersion() {
//	 return version;
//	 }
//	
//	 /**
//	 * @param version
//	 * the version to set
//	 */
//	 public void setVersion(Integer version) {
//	 this.version = version;
//	 }
	
	@Comment(value="创建时间",desc="数据的保存时间")
	@Column(updatable = false)//本属性只在save时有效,update时无效
	private Date createDate = new Date();

	@Comment("更新时间")
	@Column(insertable = false)//本属性只在update时有效,save时无效
	private Date updateDate = new Date();

	@Comment("创建人")
	@Column(length=80,updatable = false)//本属性只在save时有效,update时无效
	private String createBy;

	@Comment("最后修改人")
	@Column(length=80,insertable = false)//本属性只在update时有效,save时无效
	private String updateBy;
	
	@Comment("有效")
	@Column
	private boolean enabled = true;

	/**
	 * @return the enabled
	 */
	@JsonProperty("enabled")
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @param enabled
	 *            the enabled to set
	 */
	@JsonProperty("enabled")    
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
    @JsonProperty("pkid")    
	public void setPkId(Long id) {
		this.pkId =id;
	}

    @JsonProperty("pkid")    
	public Long getPkId() {
		return pkId;
	}
	

	/**
	 * @return the create
	 */
    @JsonSerialize(using = ExtDateSerializer.class)
    @JsonProperty("createdate")	
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param create
	 *            the create to set
	 */
    @JsonDeserialize(using = ExtDateDeserializer.class)
    @JsonProperty("createdate")    
	public void setCreateDate(Date create) {
		this.createDate = create;
	}


    @JsonProperty("createby")    
	public String getCreateBy() {
		return createBy;
	}

    @JsonProperty("createby")    
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

    @JsonSerialize(using = ExtDateSerializer.class)
    @JsonProperty("updatedate")	
	public Date getUpdateDate() {
		return updateDate;
	}

    @JsonDeserialize(using = ExtDateDeserializer.class)
    @JsonProperty("updatedate")    
	public void setUpdateDate(Date update) {
		this.updateDate = update;
	}

    @JsonProperty("updateby")    
	public String getUpdateBy() {
		return updateBy;
	}

    @JsonProperty("updateby")    
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	
}
