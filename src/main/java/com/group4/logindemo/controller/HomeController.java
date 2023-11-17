package com.group4.logindemo.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.group4.logindemo.model.User;
import com.group4.logindemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // 你可以添加任何需要在主页上显示的模型属性
        // model.addAttribute("attributeName", attributeValue);

        // 返回主页视图的名称，确保你有一个名为'home.html'的模板文件在'resources/templates'目录下
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "error", required = false) String error,
                        Model model, HttpSession session) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "您已成功注销。");
        }
        if (error != null) {
            model.addAttribute("loginError", "用户名或密码错误");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        logger.info("Registering user: {}", user.getUsername());
        userService.createUser(user);
        return "redirect:/login"; // 注册成功后重定向到登录页面
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("image/jpeg"); // 确保设置了正确的内容类型
        String capText = captchaProducer.createText();
        session.setAttribute("captcha", capText);

        BufferedImage bi = captchaProducer.createImage(capText);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
