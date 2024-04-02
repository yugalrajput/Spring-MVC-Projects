package com.rays.ctl;

import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.UserServiceImpl;
import com.rays.util.DataUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/ctl/User")
public class UserCtl {

    @Autowired
    public UserServiceImpl service;

    @ModelAttribute("form")
    public void preload(Model model) {
        List list = service.search(null, 0, 0);
        model.addAttribute("userList", list);
    }

    @GetMapping
    public String display(@ModelAttribute("form") UserForm form, @RequestParam(required = false) Long id, Model model) {

        if (id != null && id > 0) {
            UserDTO dto = service.findByPk(id);
            form.setId(dto.getId());
            form.setFirstName(dto.getFirstName());
            form.setLastName(dto.getLastName());
            form.setLogin(dto.getLogin());
            form.setPassword(dto.getPassword());
            form.setDob(DataUtility.dateToString(dto.getDob()));
            form.setAddress(dto.getAddress());
        }

        return "UserView";
    }

    @PostMapping
    public String submit(@ModelAttribute("form") @Valid UserForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "UserView";
        }

        UserDTO dto = new UserDTO();
        dto.setId(form.getId());
        dto.setFirstName(form.getFirstName());
        dto.setLastName(form.getLastName());
        dto.setLogin(form.getLogin());
        dto.setPassword(form.getPassword());
        dto.setDob(DataUtility.stringToDate(form.getDob()));
        dto.setAddress(form.getAddress());

        if (form.getId() > 0) {
            service.update(dto);
        } else {
            service.add(dto);
        }
        return "UserView";
    }

    @GetMapping("search")
    public String display(@ModelAttribute("form") UserForm form, Model model) {

        int pageNo = 1;
        int pageSize = 5;

        List list = service.search(null, pageNo, pageSize);

        form.setPageNo(pageNo);

        model.addAttribute("list", list);

        return "UserListView";

    }

    @PostMapping("search")
    public String search(@ModelAttribute("form") UserForm form, @RequestParam(required = false) String operation,
                         Model model) {

        UserDTO dto = null;

        int pageNo = 1;
        int pageSize = 5;

        if (operation.equals("next")) {

            pageNo = form.getPageNo();

            pageNo++;

        }

        if (operation.equals("previous")) {

            pageNo = form.getPageNo();

            pageNo--;

        }

        if (operation.equals("search")) {

            dto = new UserDTO();

            dto.setId(form.getId());

            dto.setFirstName(form.getFirstName());

        }
        if (operation.equals("delete")) {
            if (form.getIds() != null && form.getIds().length > 0) {
                for (long id : form.getIds()) {
                    service.delete(id);
                }
            }
        }

        form.setPageNo(pageNo);

        List list = service.search(dto, pageNo, pageSize);

        model.addAttribute("list", list);

        return "UserListView";

    }


}
