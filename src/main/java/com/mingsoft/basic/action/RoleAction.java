package com.mingsoft.basic.action;

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
import org.springframework.web.bind.annotation.RequestBody;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.basic.biz.IRoleBiz;
import com.mingsoft.basic.entity.RoleEntity;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.bean.ListBean;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 角色管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：1.0<br/>
 * 创建日期：2017-8-24 23:40:55<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/basic/role")
public class RoleAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入角色业务层
	 */	
	@Autowired
	private IRoleBiz roleBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/basic/role/index");
	}
	
	/**
	 * 查询角色列表
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute RoleEntity role,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		BasicUtil.startPage();
		List roleList = roleBiz.query(role);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(roleList,(int)BasicUtil.endPage(roleList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面role_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute RoleEntity role,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(role.getRoleId() > 0){
			BaseEntity roleEntity = roleBiz.getEntity(role.getRoleId());			
			model.addAttribute("roleEntity",roleEntity);
		}
		
		return view ("/basic/role/form");
	}
	
	/**
	 * 获取角色
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute RoleEntity role,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(role.getRoleId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("role.id")));
			return;
		}
		RoleEntity _role = (RoleEntity)roleBiz.getEntity(role.getRoleId());
		this.outJson(response, _role);
	}
	
	/**
	 * 保存角色实体
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute RoleEntity role, HttpServletResponse response, HttpServletRequest request) {
		//验证角色名的值是否合法			
		if(StringUtil.isBlank(role.getRoleName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("role.name")));
			return;			
		}
		if(!StringUtil.checkLength(role.getRoleName()+"", 1, 30)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("role.name"), "1", "30"));
			return;			
		}
		//验证角色管理员编号的值是否合法			
		if(StringUtil.isBlank(role.getRoleManagerId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("role.managerid")));
			return;			
		}
		if(!StringUtil.checkLength(role.getRoleManagerId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("role.managerid"), "1", "10"));
			return;			
		}
		roleBiz.saveEntity(role);
		this.outJson(response, JSONObject.toJSONString(role));
	}
	
	/**
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId:多个roleId直接用逗号隔开,例如roleId=1,2,3,4
	 * 批量删除角色
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestBody List<RoleEntity> roles,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[roles.size()];
		for(int i = 0;i<roles.size();i++){
			ids[i] = roles.get(i).getRoleId();
		}
		roleBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新角色信息角色
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute RoleEntity role, HttpServletResponse response,
			HttpServletRequest request) {
		//验证角色名的值是否合法			
		if(StringUtil.isBlank(role.getRoleName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("role.name")));
			return;			
		}
		if(!StringUtil.checkLength(role.getRoleName()+"", 1, 30)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("role.name"), "1", "30"));
			return;			
		}
		//验证角色管理员编号的值是否合法			
		if(StringUtil.isBlank(role.getRoleManagerId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("role.managerid")));
			return;			
		}
		if(!StringUtil.checkLength(role.getRoleManagerId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("role.managerid"), "1", "10"));
			return;			
		}
		roleBiz.updateEntity(role);
		this.outJson(response, JSONObject.toJSONString(role));
	}
		
}