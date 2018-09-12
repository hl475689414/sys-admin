package com.cn.layui.sysadmin.service.impl;

import com.cn.layui.sysadmin.dao.SystemMenuMapper;
import com.cn.layui.sysadmin.entity.SystemMenu;
import com.cn.layui.sysadmin.service.SystemMenuService;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("sysMenuService")
public class SystemMenuServiceImpl  implements SystemMenuService {

    @Resource
    private SystemMenuMapper systemMenuMapper;


    /**
     * 获取用户角色权限列表
     * @param userId
     * @return
     */
    @Override
    public Set<String> listPerms(int userId) {
        List<String> perms = systemMenuMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    public List<Object> getSysMenuList() {
        List<SystemMenu> listmenu = systemMenuMapper.getAllMenuList();
        JSONArray jsonArray = JSONArray.fromObject(listmenu);
        System.out.println("原数据："+jsonArray);
       if(listmenu!=null){
           listmenu.remove(0);
           listmenu.remove(0);
           listmenu.remove(0);
       }
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setId(59);
        systemMenu.setParentId(0);
        systemMenu.setName("后台首页");
        systemMenu.setUrl("main");
        systemMenu.setIcon("icon-computer");
        listmenu.add(0, systemMenu);
        listAllTree = new ArrayList<>();
        List<Object> list = generateAllMenuTree(listmenu);

        return list;
    }

    /**
     * 获取所有一级菜单
     */
    public List<Object> listAllTree = null; //最终树状结构结果集
    public List<Object> generateAllMenuTree(List<SystemMenu> menuList) {
        for (SystemMenu systemMenu : menuList) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            if(systemMenu.getParentId() == 0){
                mapArr.put("id", systemMenu.getId());
                mapArr.put("parentId", systemMenu.getParentId());
                mapArr.put("title", systemMenu.getName());
                mapArr.put("href", systemMenu.getUrl());
                mapArr.put("perms", systemMenu.getPerms());
                mapArr.put("type", systemMenu.getType());
               // mapArr.put("icon", systemMenu.getIcon());
                mapArr.put("spread",false);
                if(allMenuChild(systemMenu.getId(), menuList).size() > 0) {
                    mapArr.put("children", allMenuChild(systemMenu.getId(), menuList));
                }
                listAllTree.add(mapArr);
            }
        }
        return listAllTree;
    }

    /**
     * 获取所有一级菜单下的所有菜单
     * @param id
     * @param menuList
     * @return
     */
    public List<?> allMenuChild(int id, List<SystemMenu> menuList){
        List<Object> lists = new ArrayList<Object>();
        for(SystemMenu systemMenu : menuList){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(systemMenu.getParentId() == id){
                childArray.put("id", systemMenu.getId());
                childArray.put("parentId", systemMenu.getParentId());
                childArray.put("title", systemMenu.getName());
                childArray.put("href", systemMenu.getUrl());
                childArray.put("perms", systemMenu.getPerms());
                childArray.put("type", systemMenu.getType());
                //childArray.put("icon", systemMenu.getIcon());
                childArray.put("spread", false);
                if(menuChild(systemMenu.getId(), menuList).size() > 0) {
                    childArray.put("children", allMenuChild(systemMenu.getId(), menuList));
                }
                lists.add(childArray);
            }
        }
        return lists;
    }

    /**
     * 获取所有一级菜单下的所有菜单
     * @param id
     * @param menuList
     * @return
     */
    public List<?> menuChild(int id, List<SystemMenu> menuList){
        List<Object> lists = new ArrayList<Object>();
        for(SystemMenu systemMenu : menuList){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if(systemMenu.getParentId() == id){
                childArray.put("id", systemMenu.getId());
                childArray.put("title", systemMenu.getName());
                if(menuChild(systemMenu.getId(), menuList).size() > 0) {
                    childArray.put("children", menuChild(systemMenu.getId(), menuList));
                }
                lists.add(childArray);
            }
        }
        return lists;
    }
}
