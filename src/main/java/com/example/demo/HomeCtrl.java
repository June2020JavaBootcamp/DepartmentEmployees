package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeCtrl {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping("/")
    public String homepg(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "homepg";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        Employee employee = employeeRepository.findByUsername(username);
        model.addAttribute("employee", employee);
        return "secure";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegisterPage(@Valid @ModelAttribute("employee") Employee employee,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.clearPassword();
            model.addAttribute("employee", employee);
            return "register";
        }
        else{
            model.addAttribute("employee", employee);
            model.addAttribute("message", "New user account created");
            //user.setEnabled(true);
            employeeRepository.save(employee);

            Role role = new Role(employee.getUsername(), "ROLE_USER");
            roleRepository.save(role);
            //return "redirect:/";
            return "index";
        }
    }

    @RequestMapping("/newDepart")
    public String newDepart(Model model) {

        model.addAttribute("department", new Department());
        return "newdepart";
    }

    @PostMapping("/processDepart")
    public String processDepart(@ModelAttribute Department department) {
        departmentRepository.save(department);
        return "redirect:/";
    }

    @RequestMapping("/updateDepart/{id}")
    public String updateDepart(@PathVariable("id") Long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "newdepart";
    }

    @RequestMapping("/showDepart/{id}")
    public String showDepart(@PathVariable("id") Long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "showdepart";
    }

    @RequestMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") Long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "register";
    }

}
