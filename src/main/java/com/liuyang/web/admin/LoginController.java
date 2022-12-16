package com.liuyang.web.admin;

import com.liuyang.bean.User;
import com.liuyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author liuyang
 * @create 2022-08-11-10:39
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //去登录页
    @GetMapping(value = "/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            //登录成功保存用户信息
            session.setAttribute("loginUser",user);
            //登录成功则重定向到,防止表单重复提交
            return "redirect:/index.html";
        }else {
            //登录失败则返回登录页
            model.addAttribute("message","账号密码错误，请重新输入!");
            return "admin/login";
        }
    }

    //到index页面去
    @GetMapping(value = "/index.html")
    public String indexPage() {
        return "admin/index";
    }

    //退出
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "admin/login";
    }



}
