package com.ybc.shiro.Controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ybc.shiro.Entity.BaseResponse;
import com.ybc.shiro.Entity.User;
import org.apache.catalina.connector.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {

    @RequiresPermissions("user:add")
    @RequestMapping("add")
    public String userAdd(Model model){
        model.addAttribute("value", "新增用户信息");
        return "user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("delete")
    public String userDelete(Model model){
        model.addAttribute("value", "删除用户");
        return "user";
    }

    @RequiresPermissions("user:user")
    @RequestMapping("list")
    public String userList(Model model) {
        model.addAttribute("value", "获取用户信息");
        return "user";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }
}
