package com.xinguan.security;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeService employeeService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findEmployeeByUsername(username);
        if (employee != null) {
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            //grantedAuthorityList.add(new CustomGrantedAuthority("MY_ROLE1", "MY_MENU1"));
            for (Role role : employee.getRoles()) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
            }
            String password = "{MD5}" + employee.getPassword();
            return new User(username, password, grantedAuthorityList);
        }else{
            return null;
        }

    }
}
