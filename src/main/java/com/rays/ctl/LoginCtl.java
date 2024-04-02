package com.rays.ctl;

import com.rays.dto.UserDTO;
import com.rays.form.LoginForm;
import com.rays.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "Login")
public class LoginCtl {

    @Autowired
    public UserServiceImpl service;


    @GetMapping
    public String display(@ModelAttribute("form") LoginForm form, @RequestParam(required = false) String operation, HttpSession session) {

        if (operation != null && operation.equals("logout")) {
            session.invalidate();
        }
        return "LoginView";
    }


    @PostMapping
    public String submit(@ModelAttribute("form") LoginForm form, @RequestParam(required = false) String operation,
                         HttpSession session) {

        if (operation.equals("signUp")) {
            return "redirect:Register";
        }

        UserDTO dto = service.authenticate(form.getLogin(), form.getPassword());

        if (dto != null) {
            session.setAttribute("user", dto);
            return "redirect:Welcome";
        }
        return "LoginView";
    }


}
