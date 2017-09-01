package com.mingsoft.basic.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.IManagerBiz;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.IRoleModelBiz;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.entity.RoleModelEntity;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 模块控制层
 * @author 史爱华
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-6-29<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/model")
public class ModelAction extends BaseAction {

	/**
	 * 注入模块业务层
	 */
	@Autowired
	private IModelBiz modelBiz;
	
	@Autowired
	private IManagerBiz managerBiz;
	/**
	 * 角色模块关联业务层
	 */
	@Autowired
	private IRoleModelBiz roleModelBiz;

	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request,ModelMap mode){
		List<BaseEntity> parentModelList = modelBiz.queryAll();
		mode.addAttribute("parentModelList", JSONArray.toJSONString(parentModelList));
		return view ("/model/index");
	}

	
	/**
	 * 查询模块表列表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		List modelList = modelBiz.query(modelEntity);
		EUListBean _list = new EUListBean(modelList, modelList.size());
		this.outJson(response,net.mingsoft.base.util.JSONArray.toJSONString(_list));
	}
	
	/**
	 * 获取模块表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(modelEntity.getModelId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("model.id")));
			return;
		}
		//根据父模块id查寻模块
		ModelEntity _model = (ModelEntity)modelBiz.getEntity(modelEntity.getModelId());
		if(_model != null){
			Map<String, ModelEntity> mode = new HashMap<String, ModelEntity>();
			ModelEntity parentModel = (ModelEntity) modelBiz.getEntity(_model.getModelModelId());
			mode.put("parentModel", parentModel);
			mode.put("model", _model);
			this.outJson(response, JSONObject.toJSONString(mode));
			return;
		}
		this.outJson(response, _model);
	}
	
	/**
	 * 保存模块表实体
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute ModelEntity model, HttpServletResponse response, HttpServletRequest request) {
		//获取管理员id并赋值给模块的id
		model.setModelId(getManagerId(request));
		// 获取模块保存时间
		model.setModelDatetime(new Timestamp(System.currentTimeMillis()));
		if (!StringUtil.checkLength(model.getModelTitle(), 1, 20) ) {
			this.outJson(response,getResString("err.length",this.getResString("modelTitle"),"2","20"));
			return;
		}
		if(!StringUtil.checkLength(model.getModelCode(), 1, 20)){
			this.outJson(response,getResString("err.length",this.getResString("modelCode"),"2","20"));
			return;
		}
		//判断图标是否为空，不为空去掉,图标地址中含有的“|”
		//空值判断
		if(!StringUtil.isBlank(model.getModelIcon())) {
			model.setModelIcon( model.getModelIcon().replace("|", ""));
		}
		//添加父级id集合
		ModelEntity parent = (ModelEntity) modelBiz.getEntity(model.getModelModelId());
		String parentIds="";
		if(parent != null){
			parentIds = parent.getModelParentIds()+","+model.getModelModelId();
		}else if(model.getModelModelId() > 0){
			parentIds = model.getModelModelId()+"";
		}
		model.setModelParentIds(parentIds);
		modelBiz.saveEntity(model);
		//返回模块id到页面
		this.outJson(response, ModelCode.ROLE, true,String.valueOf(model.getModelId()));
	}
	
	/**
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId:多个modelId直接用逗号隔开,例如modelId=1,2,3,4
	 * 批量删除模块表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("ids", ",");
		modelBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新模块表信息模块表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	public void update(@ModelAttribute ModelEntity model, HttpServletResponse response,
			HttpServletRequest request) {
		if (!StringUtil.checkLength(model.getModelTitle(), 2, 20)
				|| !StringUtil.checkLength(model.getModelCode(), 2, 20)) {
			return;
		} 
		//判断图标是否为空，不为空去掉,图标地址中含有的“|”
		//空值判断
		if(!StringUtil.isBlank(model.getModelIcon())) {
			model.setModelIcon( model.getModelIcon().replace("|", ""));
		}
		//添加父级id集合
		ModelEntity parent = (ModelEntity) modelBiz.getEntity(model.getModelModelId());
		String parentIds="";
		if(parent != null){
			parentIds = parent.getModelParentIds()+","+model.getModelModelId();
		}else if(model.getModelModelId() > 0){
			parentIds = model.getModelModelId()+"";
		}
		model.setModelParentIds(parentIds);
		modelBiz.updateEntity(model);		
		this.outJson(response, ModelCode.ROLE, true,String.valueOf(model.getModelId()));
	}
	
	/**
	 * 根据管理员ID查询模块集合
	 * @param managerId 管理员id
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/{managerId}/queryModelByRoleId")
	public void queryModelByRoleId(@PathVariable int managerId,HttpServletRequest request, HttpServletResponse response) {
		ManagerEntity manager =(ManagerEntity) managerBiz.getEntity(managerId);
		if(manager==null){
			return;
		}
		List<BaseEntity> modelList = new ArrayList<BaseEntity>();
		modelList = modelBiz.queryModelByRoleId(manager.getManagerRoleID());
		this.outJson(response, null,true, JSONObject.toJSONString(modelList));

	}
	
	/**
	 * 查询模块表列表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/modelList")
	@ResponseBody
	public void modelList(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		int roleId = BasicUtil.getInt("roleId");
		List<ModelEntity> modelList = modelBiz.query(modelEntity);
		List<ModelEntity> _modelList = new ArrayList<>();
		List<RoleModelEntity> roleModelList = new ArrayList<>();
		if(roleId>0){
			roleModelList = roleModelBiz.queryByRoleId(roleId);
		}
		//组织子数据
		for(ModelEntity _model : modelList){
			if(_model.getModelIsMenu() == 1){
				_model.setModelChildList(new ArrayList<ModelEntity>());
				_modelList.add(_model);
			}else if(_model.getModelIsMenu() == 0){
				for(ModelEntity _modelEntity : _modelList){
					if(_model.getModelModelId() == _modelEntity.getModelId()){
						for(RoleModelEntity roleModelEntity : roleModelList){
							if(roleModelEntity.getModelId() == _model.getModelId()){
								_model.setChick(1);
							}
						}
						_modelEntity.getModelChildList().add(_model);
					}
				}
			}
		}
		EUListBean _list = new EUListBean(_modelList, _modelList.size());
		this.outJson(response,net.mingsoft.base.util.JSONArray.toJSONString(_list));
	}
}
