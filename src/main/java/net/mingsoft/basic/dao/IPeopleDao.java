package net.mingsoft.basic.dao;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.*;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import net.mingsoft.basic.entity.PeopleEntity;
 
/**
 * 通用用户与信息一对多表持久层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
@Component("IBasicPeopleDao")
public interface IPeopleDao extends IBaseDao {
	/**
	 * 读取用户预览记录
	 * @param appId 应用编号
	 * @param modelId 模块编号
	 * @param peopleId 用户编号
	 * @return
	 */
	List queryByPeople(@Param("appId")int appId, @Param("modelId")int modelId, @Param("peopleId")int peopleId);
}