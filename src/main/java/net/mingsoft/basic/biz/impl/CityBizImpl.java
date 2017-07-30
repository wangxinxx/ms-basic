/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.mingsoft.basic.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.*;
import java.util.*;
import net.mingsoft.basic.entity.CityEntity;
import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.biz.ICityBiz;
import net.mingsoft.basic.dao.ICityDao;

/**
 * 省市县镇村数据管理持久化层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
 @Service("cityBizImpl")
public class CityBizImpl extends BaseBizImpl implements ICityBiz {

	
	@Autowired
	private ICityDao cityDao;
	
	
		@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return cityDao;
	} 
	@Override
	public List<CityEntity> queryProvince() {
		return cityDao.queryProvince();
	}
	@Override
	public List<CityEntity> queryCity(CityEntity cityEntity) {
		return cityDao.queryCity(cityEntity);
	}
	@Override
	public List<CityEntity> queryCounty(CityEntity cityEntity) {
		return cityDao.queryCounty(cityEntity);
	}
	@Override
	public List<CityEntity> queryTown(CityEntity cityEntity) {
		return cityDao.queryTown(cityEntity);
	}
	@Override
	public List<CityEntity> queryVillage(CityEntity cityEntity) {
		return cityDao.queryVillage(cityEntity);
	}
	@Override
	public List<CityBean> queryByTier(int tier) {
		List<CityEntity> cityList = cityDao.queryAll();
		Map<Long,String> province = new HashMap<>();
		Map<Long,Map<Long,String>> city = new HashMap<>();
		Map<Long,Map<Long,String>> county = new HashMap<>();
		Map<Long,Map<Long,String>> town = new HashMap<>();
		Map<Long,Map<Long,String>> village = new HashMap<>();
		for(CityEntity cityEntity : cityList){
			//组织省级／市级／县级／镇级／村级数据，并保存。
			if(tier>=1){	
				//组织省、自治区、直辖市的数据
				if(province.get(cityEntity.getProvinceId()) == null){
					province.put(cityEntity.getProvinceId(), cityEntity.getProvinceName());
				}
				if(tier>=2){
					//组织市的数据
					if(city.get(cityEntity.getProvinceId()) != null){		//如果当前map中已包含当前省级，那么最近向value中填充市级数据。
						city.get(cityEntity.getProvinceId()).put(cityEntity.getCityId(), cityEntity.getCityName());
					}else{		//否则，直接添加省级，并将当前市数据填充进value
						Map<Long,String> tempCity = new HashMap<>();
						tempCity.put(cityEntity.getCityId(), cityEntity.getCityName());
						city.put(cityEntity.getProvinceId(), tempCity);
					}
					if(tier>=3){
						//组织县、区的数据
						if(county.get(cityEntity.getCityId()) != null){
							county.get(cityEntity.getCityId()).put(cityEntity.getCountyId(), cityEntity.getCountyName());
						}else{
							Map<Long,String> tempCounty = new HashMap<>();
							tempCounty.put(cityEntity.getCountyId(), cityEntity.getCountyName());
							county.put(cityEntity.getCityId(), tempCounty);
						}
						if(tier>=4){
							//组织镇、街道的数据
							if(town.get(cityEntity.getCountyId()) != null){
								town.get(cityEntity.getCountyId()).put(cityEntity.getTownId(), cityEntity.getTownName());
							}else{
								Map<Long,String> tempTown = new HashMap<>();
								tempTown.put(cityEntity.getTownId(), cityEntity.getTownName());
								town.put(cityEntity.getCountyId(), tempTown);
							}
							if(tier>=5){
								//组织村的数据
								if(village.get(cityEntity.getTownId()) != null){
									village.get(cityEntity.getTownId()).put(cityEntity.getVillageId(), cityEntity.getVillageName());
								}else{
									Map<Long,String> tempVillage = new HashMap<>();
									tempVillage.put(cityEntity.getVillageId(), cityEntity.getVillageName());
									village.put(cityEntity.getTownId(), tempVillage);
								}
							}
						}
					}
					
				}
			}
			
		}
		//数据组织返回格式
		List<CityBean> cityBeanList = new ArrayList<>();
		if(tier >= 1){
			CityBean provinceBean = new CityBean();
			//遍历省级数据，组织第一级
			for (Long provinceKey : province.keySet()) {
			    String provinceName = province.get(provinceKey);  
			    provinceBean.setProvinceId(provinceKey);
			    provinceBean.setProvinceName(provinceName);
			    provinceBean.setCityList(new ArrayList<>());
				if(tier >= 2){
					//遍历市级数据，组织第二级
					Map<Long,String> cityMap = city.get(provinceKey);
					for(Long cityKey : cityMap.keySet()){
						String cityName = cityMap.get(cityKey) ;
						CityBean cityBean = new CityBean();
						cityBean.setCityId(cityKey);
						cityBean.setCityName(cityName);
						cityBean.setCountyList(new ArrayList<>());
						if(tier >= 3){
							//遍历县级数据，组织第三极
							Map<Long,String> countyMap = county.get(cityKey);
							for(Long countyKey : countyMap.keySet()){
								String countyName = countyMap.get(countyKey);
								CityBean countyBean = new CityBean();
								countyBean.setCountyId(countyKey);
								countyBean.setCountyName(countyName);
								countyBean.setTownList(new ArrayList<>());
								if(tier >= 4){
									//遍历镇数据，组织第四级
									Map<Long,String> townMap = town.get(countyKey);
									for(Long townKey : townMap.keySet()){
										String townName = townMap.get(townKey);
										CityBean townBean = new CityBean();
										townBean.setTownId(townKey);
										townBean.setTownName(townName);
										townBean.setVillageList(new ArrayList<>());
										if(tier >= 5){
											//遍历村数据，组织第五级
											Map<Long,String> villageMap = village.get(townKey);
											for(Long villageKey : villageMap.keySet()){
												String villageName = villageMap.get(villageKey);
												CityBean villageBean = new CityBean();
												villageBean.setVillageId(villageKey);
												villageBean.setVillageName(villageName);
												townBean.getVillageList().add(villageBean);
											}
										}
										countyBean.getTownList().add(townBean);
									}
								}
								cityBean.getCountyList().add(countyBean);
							}
						}
						provinceBean.getCityList().add(cityBean);
					}
				}
				cityBeanList.add(provinceBean);
			}  
		}
		return cityBeanList;
	}
}