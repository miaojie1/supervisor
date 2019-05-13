package com.xinguan.job;

import com.xinguan.Application;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import com.xinguan.workprocess.repository.ProjectSupervisionDepartmentRepository;
import com.xinguan.workprocess.service.ProjectSupervisionDepartmentService;
import com.xinguan.workresult.model.SupervisionLog;
import com.xinguan.workresult.repository.SupervisionLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.ApplicationContextTestUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author MJ
 */
@Component
@EnableScheduling
public class SupervisionLogJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupervisionLog.class);

    @Autowired
    private ProjectSupervisionDepartmentService projectSupervisionDepartmentService;
    @Autowired
    private SupervisionLogRepository supervisionLogRepository;

    //静态初始化
    public static SupervisionLogJob supervisionLogJob;
    //保证Bean初始化前已经装配了属性
    @PostConstruct
    public void init() {
        supervisionLogJob = this;
    }

    // 每天6点执行一次
    @Scheduled(cron = "0 0 6 * * ? ")
    public void execute() {
        LOGGER.info("backUp system data job start");
        createSupervisionLog();
        LOGGER.info("backUp system data job end");
    }

    /**
     * 根据监理工程师创建监理日志
     */
    public void createSupervisionLog(){
        List<ProjectSupervisionDepartment> allSuperDeparts = projectSupervisionDepartmentService.listAllProSuperDeparts();
//        List<ProjectSupervisionDepartment> allSuperDeparts = projectSupervisionDepartmentRepository.findAll();
        Set<Employee> supeEngineers=new HashSet<>();
        for(ProjectSupervisionDepartment department: allSuperDeparts){
            supeEngineers.add(department.getMajor());
        }
        for (Employee employee:supeEngineers){
            SupervisionLog supervisionLog =new SupervisionLog();
            supervisionLog.setDate(new Date());
            supervisionLog.setSupervisionEngineer(employee);
            supervisionLog.setPosition(employee.getDepartmentPosition());
            supervisionLogRepository.saveAndFlush(supervisionLog);
        }
    }


}
