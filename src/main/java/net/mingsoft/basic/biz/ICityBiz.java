package net.mingsoft.basic.biz;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.util.*;
import java.util.*;

import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.entity.CityEntity;

/**
 * 省市县镇村数据业务
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
public interface ICityBiz extends IBaseBiz {
	/**
	 * 查询省／直辖市／自治区
	 * @return 省／直辖市／自治区列表
	 * province_id 
	 * province_name
	 */
	public List<CityEntity> queryProvince();
	/**
	 * 查询市
	 * @param cityEntity 城市实体
	 * @return 市级列表
	 * city_id
	 * city_name
	 */
	public List<CityEntity> queryCity(CityEntity cityEntity);
	/**
	 * 查询区／县
	 * @param cityEntity
	 * @return 区／县列表
	 * county_id
	 * county_name
	 */
	public List<CityEntity> queryCounty(CityEntity cityEntity);
	/**
	 * 查询街道／镇
	 * @param cityEntity
	 * @return 街道／镇列表
	 * town_id
	 * town_name
	 */
	public List<CityEntity> queryTown(CityEntity cityEntity);
	/**
	 * 查询村委会
	 * @param cityEntity
	 * @return 村委会列表
	 * village_id
	 * village_name
	 */
	public List<CityEntity> queryVillage(CityEntity cityEntity);
	
	public List<CityBean> queryByTier(int tier);

}