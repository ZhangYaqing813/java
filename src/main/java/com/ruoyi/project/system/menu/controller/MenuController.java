package com.ruoyi.project.system.menu.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.JSON;
import com.ruoyi.project.system.menu.domain.Menu;
import com.ruoyi.project.system.menu.service.IMenuService;
import com.ruoyi.project.system.role.domain.Role;

/**
 * 角色信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController
{

    private String prefix = "system/menu";

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("system:menu:view")
    @GetMapping()
    public String menu()
    {
        return prefix + "/menu";
    }

    @RequiresPermissions("system:menu:list")
    @GetMapping("/list")
    @ResponseBody
    public List<Menu> list()
    {
        List<Menu> menuList = menuService.selectMenuAll();
        return menuList;
    }

    /**
     * 删除菜单
     */
    @Log(title = "系统管理", action = "菜单管理-删除菜单")
    @RequiresPermissions("system:menu:remove")
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public JSON remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.deleteMenuById(menuId) > 0)
        {
            return JSON.ok();
        }
        else
        {
            return JSON.error(1, "删除失败");
        }
    }

    /**
     * 修改菜单
     */
    @Log(title = "系统管理", action = "菜单管理-修改菜单")
    @RequiresPermissions("system:menu:edit")
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, Model model)
    {
        Menu menu = menuService.selectMenuById(menuId);
        model.addAttribute("menu", menu);
        return prefix + "/edit";
    }
    
    /**
     * 新增
     */
    @Log(title = "系统管理", action = "菜单管理-新增菜单")
    @RequiresPermissions("system:menu:add")
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, Model model)
    {
        Menu menu = menuService.selectMenuById(parentId);
        model.addAttribute("menu", menu);
        return prefix + "/add";
    }

    /**
     * 保存菜单
     */
    @Log(title = "系统管理", action = "菜单管理-保存菜单")
    @RequiresPermissions("system:menu:save")
    @PostMapping("/save")
    @ResponseBody
    public JSON save(Menu menu)
    {
        if (menuService.saveMenu(menu) > 0)
        {
            return JSON.ok();
        }
        return JSON.error();
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon()
    {
        return prefix + "/icon";
    }

    /**
     * 加载菜单列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String, Object>> treeData(Role role)
    {
        List<Map<String, Object>> tree = menuService.selectMenuTree(role);
        return tree;
    }
}