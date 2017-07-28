package net.mingsoft.basic.action.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.mingsoft.basic.biz.IPeopleBiz;
import net.mingsoft.basic.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 通用用户与信息一对多表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-27 14:47:29<br/>
 * 历史修订：<br/>
 */
@Controller("webBasicPeopleAction")
@RequestMapping("/basic/people")
public class PeopleAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入通用用户与信息一对多表业务层
	 */	
	@Resource(name="basicPeopleBizImpl")
	private IPeopleBiz peopleBiz;
	
	
	/**
	 * 查询通用用户与信息一对多表列表
	 * @param people 通用用户与信息一对多表实体
	 * <i>people参数包含字段信息参考：</i><br/>
	 * bpId <br/>
	 * bpBasicId 信息编号<br/>
	 * bpPeopleId 用户编号<br/>
	 * bpDatetime 创建时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * bpId: <br/>
	 * bpBasicId: 信息编号<br/>
	 * bpPeopleId: 用户编号<br/>
	 * bpDatetime: 创建时间<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute PeopleEntity people,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List peopleList = peopleBiz.query(people);
		BasicUtil.endPage(peopleList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(peopleList, "yyyy-MM-dd"));
	}
		
}