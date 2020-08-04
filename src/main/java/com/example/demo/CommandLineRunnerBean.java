package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public void run(String... args) {
        Department department = new Department();
        department.setName("HR");
        departmentRepository.save(department);

        Department department2 = new Department();
        department2.setName("IT");
        departmentRepository.save(department2);


        Employee employee = new Employee("bart", "bart@domain.com", "bart",
                "Bart", "Simpson", true);
        Role userRole = new Role("bart", "ROLE_USER");

        employee.setDepartment(department);
        employee.setJobTitle("Staff");
        employeeRepository.save(employee);
        roleRepository.save(userRole);

        Employee admin = new Employee("super", "super@domain.com", "super",
                "Super", "Hero", true);
        Role adminRole1 = new Role("super", "ROLE_ADMIN");
        Role adminRole2 = new Role("super", "ROLE_USER");
        admin.setDepartment(department);
        admin.setJobTitle("Manager");

        employeeRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);


    }
}
