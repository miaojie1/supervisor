package com.xinguan.interceptor;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.usermanage.model.Operation;
import com.xinguan.usermanage.model.Role;
import com.xinguan.usermanage.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zhangzhan
 * @date 2019-04-04 20:12
 */
@Component
public class UrlInterceptor implements HandlerInterceptor {

    private EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlInterceptor.class);

    @Autowired
    public UrlInterceptor(EmployeeService employeeService) {
        Assert.notNull(employeeService, "employeeService must not be null");
        this.employeeService = employeeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查用户是否已登录
        final Employee employee = employeeService.getCurrentUser();
        boolean flag = false;
        if (employee != null) {
            //检查当前用户拥有的角色是否有权限访问请求的资源
            String accessUrl = request.getRequestURI();
            String url = accessUrl.substring(accessUrl.indexOf("/", 2));
            a:
            for (Role role : employee.getRoles()) {
                for (Menu menu : role.getMenus()) {
                    if (Objects.equals(menu.getUrl(), url)) {
                        flag = true;
                        break a;
                    }
                    for (Operation operation : menu.getOperation()) {
                        if (Objects.equals(operation.getButtonUrl(), url)) {
                            flag = true;
                            break a;
                        }
                    }
                }
            }
        }

        if (!flag) {
            response.setStatus(401);
            response.getWriter().write("Invalid access");
            LOGGER.warn("User:" + (employee == null ? "" : employee.getUsername()) + " try to access " + request.getRequestURI());
        }
        return flag;
    }


}
