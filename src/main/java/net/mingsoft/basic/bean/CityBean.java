package net.mingsoft.basic.bean;

import java.util.List;

import net.mingsoft.basic.entity.CityEntity;
/**
 * 城市数据格式bean
 * @author qiu
 *
 */
public class CityBean extends CityEntity{
	/**
	 * 省，直辖市，自治区
	 */
	private List<Object> provinceList;
	/**
	 * 市
	 */
	private List<Object> cityList;
	/**
	 * 区，县
	 */
	private List<Object> countyList;
	/**
	 * 镇，街道
	 */
	private List<Object> townList;
	/**
	 * 村
	 */
	private List<Object> villageList;
	public List<Object> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<Object> provinceList) {
		this.provinceList = provinceList;
	}
	public List<Object> getCityList() {
		return cityList;
	}
	public void setCityList(List<Object> cityList) {
		this.cityList = cityList;
	}
	public List<Object> getCountyList() {
		return countyList;
	}
	public void setCountyList(List<Object> countyList) {
		this.countyList = countyList;
	}
	public List<Object> getTownList() {
		return townList;
	}
	public void setTownList(List<Object> townList) {
		this.townList = townList;
	}
	public List<Object> getVillageList() {
		return villageList;
	}
	public void setVillageList(List<Object> villageList) {
		this.villageList = villageList;
	}
	
	
}
