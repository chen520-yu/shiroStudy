package com.ybc.shiro.Controller;

import com.ybc.shiro.Entity.BaseResponse;
import com.ybc.shiro.Entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public BaseResponse login(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            return BaseResponse.ok();
        } catch (UnknownAccountException e) {
            return BaseResponse.wrong(e, 500, "UnknownAccountException");
        } catch (IncorrectCredentialsException e) {
            return BaseResponse.wrong(e, 500, "IncorrectCredentialsException");
        } catch (LockedAccountException e) {
            return BaseResponse.wrong(e, 500, "LockedAccountException");
        } catch (AuthenticationException e) {
            return BaseResponse.wrong(e, 500, "AuthenticationException");
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        // 登录成后，即可通过Subject获取登录的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "index";
    }
}
