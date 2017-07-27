package net.mingsoft.basic.action.web;

import java.util.List;

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
@Controller("webPeopleAction")
@RequestMapping("/basic/people")
public class PeopleAction extends net.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入通用用户与信息一对多表业务层
	 */	
	@Autowired
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
	
	
	/**
	 * 获取通用用户与信息一对多表
	 * @param people 通用用户与信息一对多表实体
	 * <i>people参数包含字段信息参考：</i><br/>
	 * bpId <br/>
	 * bpBasicId 信息编号<br/>
	 * bpPeopleId 用户编号<br/>
	 * bpDatetime 创建时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * bpId: <br/>
	 * bpBasicId: 信息编号<br/>
	 * bpPeopleId: 用户编号<br/>
	 * bpDatetime: 创建时间<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute PeopleEntity people,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(people.getBpId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("bp.id")));
			return;
		}
		PeopleEntity _people = (PeopleEntity)peopleBiz.getEntity(people.getBpId());
		this.outJson(response, _people);
	}
	
	/**
	 * 保存通用用户与信息一对多表实体
	 * @param people 通用用户与信息一对多表实体
	 * <i>people参数包含字段信息参考：</i><br/>
	 * bpId <br/>
	 * bpBasicId 信息编号<br/>
	 * bpPeopleId 用户编号<br/>
	 * bpDatetime 创建时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * bpId: <br/>
	 * bpBasicId: 信息编号<br/>
	 * bpPeopleId: 用户编号<br/>
	 * bpDatetime: 创建时间<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute PeopleEntity people, HttpServletResponse response, HttpServletRequest request) {
		//验证创建时间的值是否合法			
		if(StringUtil.isBlank(people.getBpDatetime())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("bp.datetime")));
			return;			
		}
		if(!StringUtil.checkLength(people.getBpDatetime()+"", 1, 19)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("bp.datetime"), "1", "19"));
			return;			
		}
		peopleBiz.saveEntity(people);
		this.outJson(response, people);
	}

	/**
	 * @param people 通用用户与信息一对多表实体
	 * <i>people参数包含字段信息参考：</i><br/>
	 * bpId:多个bpId直接用逗号隔开,例如bpId=1,2,3,4
	 * 批量删除通用用户与信息一对多表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute PeopleEntity people,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("bpId");
		if(ids==null){
			this.outJson(response,null, false);
			return;
		}
		peopleBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新通用用户与信息一对多表信息通用用户与信息一对多表
	 * @param people 通用用户与信息一对多表实体
	 * <i>people参数包含字段信息参考：</i><br/>
	 * bpId <br/>
	 * bpBasicId 信息编号<br/>
	 * bpPeopleId 用户编号<br/>
	 * bpDatetime 创建时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * bpId: <br/>
	 * bpBasicId: 信息编号<br/>
	 * bpPeopleId: 用户编号<br/>
	 * bpDatetime: 创建时间<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute PeopleEntity people, HttpServletResponse response,
			HttpServletRequest request) {
		//验证创建时间的值是否合法			
		if(StringUtil.isBlank(people.getBpDatetime())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("bp.datetime")));
			return;			
		}
		if(!StringUtil.checkLength(people.getBpDatetime()+"", 1, 19)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("bp.datetime"), "1", "19"));
			return;			
		}
		peopleBiz.updateEntity(people);
		this.outJson(response, people);
	}
	
		
}