package com.rays.ctl;

import com.rays.dto.UserDTO;
import com.rays.form.UserRegistrationForm;
import com.rays.service.UserServiceImpl;
import com.rays.util.DataUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "Register")
public class UserRegistrationCtl {

    @Autowired
    public UserServiceImpl service;

    @GetMapping
    public String display(@ModelAttribute("form") UserRegistrationForm form) {
        return "UserRegistration";
    }

    @PostMapping
    public String submit(@ModelAttribute("form") UserRegistrationForm form) {

        UserDTO dto = new UserDTO();

        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setLogin(form.getLogin());
        dto.setPassword(form.getPassword());
        dto.setDob(DataUtility.stringToDate(form.getDob()));
        dto.setAddress(form.getAddress());

        service.add(dto);

        return "UserRegistration";
    }
}
