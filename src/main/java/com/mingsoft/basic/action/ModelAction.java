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
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
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
