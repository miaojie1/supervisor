package com.xinguan.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author zhangzhan
 */
@Configuration
public class QuartzConfig {


    /**
     * 调度器工厂配置
     *
     * @param backUpDataJobTrigger 数据备份trigger
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("backUpDataJobTrigger") CronTriggerFactoryBean backUpDataJobTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(backUpDataJobTrigger.getObject());
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setStartupDelay(10);
        return schedulerFactoryBean;
    }
}
