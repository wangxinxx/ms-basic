package net.mingsoft.basic.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 通用用户与信息一对多表实体
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
public class PeopleEntity extends BaseEntity {

	private static final long serialVersionUID = 1501138049728L;
	
	/**
	 * 
	 */
	private Integer bpId; 
	/**
	 * 信息编号
	 */
	private Integer bpBasicId; 
	/**
	 * 用户编号
	 */
	private Integer bpPeopleId; 
	/**
	 * 创建时间
	 */
	private Date bpDatetime; 
	
	public PeopleEntity(){}
	public PeopleEntity(Integer bpId) {
	this.bpId = bpId;	
	}
	
	public PeopleEntity(Integer bpBasicId) {
		this.bpBasicId = bpBasicId;	
	}
	
	public PeopleEntity(Integer bpBasicId,Integer bpPeopleId) {
		this.bpBasicId = bpBasicId;		this.bpPeopleId = bpPeopleId;	
	}
	
	public PeopleEntity(Integer bpBasicId,Integer bpPeopleId,Date bpDatetime) {
		this.bpBasicId = bpBasicId;		this.bpPeopleId = bpPeopleId;		this.bpDatetime = bpDatetime;	
	}
	
		
	/**
	 * 设置
	 */
	public void setBpId(Integer bpId) {
		this.bpId = bpId;
	}

	/**
	 * 获取
	 */
	public Integer getBpId() {
		return this.bpId;
	}
	
	/**
	 * 设置信息编号
	 */
	public void setBpBasicId(Integer bpBasicId) {
		this.bpBasicId = bpBasicId;
	}

	/**
	 * 获取信息编号
	 */
	public Integer getBpBasicId() {
		return this.bpBasicId;
	}
	
	/**
	 * 设置用户编号
	 */
	public void setBpPeopleId(Integer bpPeopleId) {
		this.bpPeopleId = bpPeopleId;
	}

	/**
	 * 获取用户编号
	 */
	public Integer getBpPeopleId() {
		return this.bpPeopleId;
	}
	
	/**
	 * 设置创建时间
	 */
	public void setBpDatetime(Date bpDatetime) {
		this.bpDatetime = bpDatetime;
	}

	/**
	 * 获取创建时间
	 */
	public Date getBpDatetime() {
		return this.bpDatetime;
	}
	
}